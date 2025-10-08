package com.dragn0007.dragnvehicles.vehicle.base;

import com.dragn0007.dragnvehicles.item.VVItems;
import com.dragn0007.dragnvehicles.util.VVTags;
import net.minecraft.ChatFormatting;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

public abstract class AbstractVehicle extends AbstractGeckolibVehicle {

    protected static final EntityDataAccessor<Float> DATA_HEALTH = SynchedEntityData.defineId(AbstractVehicle.class, EntityDataSerializers.FLOAT);
    protected static final EntityDataAccessor<Integer> DATA_TYPE = SynchedEntityData.defineId(AbstractVehicle.class, EntityDataSerializers.INT);

    protected static final Random RANDOM = new Random();
    protected static final double GRAVITY = 0.04D;

    protected final double maxSpeed;
    protected final double acceleration;
    protected final float turnRate;
    protected final int maxHealth;

    protected double speed = 0;
    public UUID owner;

    private float targetRotation = 0;
    private float currentRotation = 0;
    private static final float MAX_TURN = 7f;

    public AbstractVehicle(EntityType<? extends AbstractVehicle> type, Level level, double maxSpeed, double acceleration,
                           float turnRate, int maxHealth, double wheelWidth, double wheelLength, Vec3[] riders) {
        super(type, level, wheelWidth, wheelLength, riders);
        this.maxSpeed = maxSpeed;
        this.acceleration = acceleration;
        this.turnRate = turnRate;
        this.maxHealth = maxHealth;
    }

    public float getFrontWheelRotation(float time) {
        return (this.currentRotation + (this.targetRotation - this.currentRotation) * time) * (float)Math.PI / 180;
    }

    @OnlyIn(Dist.CLIENT)
    private void handleInput(Input input) {
        float forward = 0;
        float turn = 0;

        if(input.left) {
            turn = turnRate;
        }

        if(input.right) {
            turn = -turnRate;
        }

        if(forward != 0 && turn == 0) {
            this.targetRotation = 0;
        }

        this.currentRotation = this.targetRotation;
        if(Math.abs(this.targetRotation + turn) <= MAX_TURN) {
            this.targetRotation += turn;
        }

        float deg = this.currentRotation + this.getYRot();
        float rad = deg * (float)Math.PI / 180;

        if(forward != 0 && deg != this.getYRot()) {
            this.setYRot(deg);
        }

        this.setDeltaMovement(this.getDeltaMovement().add(-Math.sin(rad) * forward, 0, Math.cos(rad) * forward));
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
        } else {
            if(this.getControllingPassenger() instanceof LocalPlayer player) {
                if ((this.isLocked() && this.owner.equals(player.getUUID())) || (!this.isLocked())) {
                    this.handleInput(player.input);
                }
            }
        }
    }

    @Override
    public void tickRidden() {

        handleSteering();
        handleAcceleration();

        final Vec3 forward = getForward().multiply(1, 0, 1);
        final Vec3 velocity = forward.scale(speed);

        Vec3 move = collide(velocity);
        move = move.multiply(1, 0, 1); // "Flatten" the move vector so entities can individually handle their step heights.

        if (!isNoGravity())
            move = move.add(0, -GRAVITY, 0);

        setDeltaMovement(move.add(0, getDeltaMovement().y, 0));
        move(MoverType.SELF, getDeltaMovement());

        setXRot(rotlerp(getXRot(), calculateTargetXRot(), 3)); // 3 is just a magic scaling number for smoothing.
    }

    protected void handleAcceleration() {
        float forward = getForwardImpulse();

        double targetSpeed = forward * maxSpeed;
        double diff = targetSpeed - speed;
        double accel = maxSpeed / (acceleration * 20);

        speed += Mth.sign(forward == 0 ? diff : forward) * Math.min(Math.abs(accel), Math.abs(diff));
    }

    protected void handleSteering() {
        float f = (float)(speed / maxSpeed);
        float steer = -getLeftImpulse() * turnRate * f;

        if(steer == 0)
            return;

        setYRot(Mth.wrapDegrees(getYRot() + steer));
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if(!isAlive())
            return InteractionResult.PASS;

        final boolean isClientSide = level().isClientSide;
        ItemStack stack = player.getItemInHand(hand);
        Item item = stack.getItem();

        if (stack.is(VVItems.CAR_KEY.get()) && player.isShiftKeyDown() && this.getOwner().equals(player.getUUID())) {
            if (!this.isLocked()) {
                this.setLocked(true);
                player.displayClientMessage(Component.translatable("Locked").withStyle(ChatFormatting.GOLD), true);
            } else {
                this.setLocked(false);
                player.displayClientMessage(Component.translatable("Unlocked").withStyle(ChatFormatting.GOLD), true);
            }
        }

        if ((this.isLocked() && this.getOwner().equals(player.getUUID())) || (!this.isLocked())) {
            if (item instanceof DyeItem) {
                if (isClientSide)
                    return InteractionResult.SUCCESS;
                DyeItem dyeitem = (DyeItem) item;
                DyeColor dyecolor = dyeitem.getDyeColor();
                if (dyecolor != this.getColor()) {
                    this.setColor(dyecolor);
                    if (!player.getAbilities().instabuild) {
                        stack.shrink(1);
                    }

                    return InteractionResult.SUCCESS;
                }

                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                    return InteractionResult.sidedSuccess(this.level().isClientSide);
                }
            }
        }

        if(player.isSecondaryUseActive()) {

            if(tryMountMob(player))
                return InteractionResult.sidedSuccess(isClientSide);

            if(stack.is(Items.LEAD)) {
                if(isClientSide)
                    return InteractionResult.SUCCESS;

                for(Entity passenger : getPassengers()) {
                    if(passenger instanceof Mob mob) {
                        mob.stopRiding();
                        player.getItemInHand(hand).shrink(1);
                        if(mob.canBeLeashed(player))
                            mob.setLeashedTo(player, true);
                        break;
                    }
                }
                return InteractionResult.CONSUME;
            }
        }

        if(player.getVehicle() != this) {
            if(!level().isClientSide) {
                player.setYRot(getYRot());
                player.setXRot(getXRot());

                if ((this.getOwner() != null && this.isLocked() && this.getOwner().equals(player.getUUID())) || (!this.isLocked())) {
                    return player.startRiding(this) ? InteractionResult.CONSUME : InteractionResult.PASS;
                }
            }
            return InteractionResult.sidedSuccess(!level().isClientSide);
        }

        return super.interact(player, hand);
    }

    protected boolean tryMountMob(Player player) {
        Mob mob = level().getEntitiesOfClass(Mob.class, new AABB(
                        player.getX()-7, player.getY()-7, player.getZ()-7,
                        player.getX()+7, player.getY()+7, player.getZ()+7
                ), h -> h.getLeashHolder() == player
                        && !h.getType().is(VVTags.Entity_Types.CANNOT_RIDE_VEHICLE)
                ).stream().findFirst().orElse(null);

        if (mob != null && !level().isClientSide && canAddPassenger(mob)) {
            mob.startRiding(this);
        }

        return mob != null;
    }

    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity passenger) {
        final double width = getBbWidth();
        double offset = Math.sqrt(width * width + width * width) / 2 + 0.05D;

        double pWidth = passenger.getBbWidth();
        double pOffset = Math.sqrt(pWidth * pWidth + pWidth * pWidth) / 2;

        float yRot = getYRot();

        pWidth = pWidth * 0.8F;

        Vec3 wOffset = new Vec3(0, passenger.getEyeHeight(), 0);

        Vec3 pos = position().add(rotateY(new Vec3(offset + pOffset, 0, 0), yRot)); // Left
        if(isEmpty(AABB.ofSize(pos.add(wOffset), pWidth, 1.0E-6D, pWidth)))
            return pos;

        pos = position().add(rotateY(new Vec3(-offset - pOffset, 0, 0), yRot)); // Right
        if(isEmpty(AABB.ofSize(pos.add(wOffset), pWidth, 1.0E-6D, pWidth)))
            return pos;

        pos = position().add(rotateY(new Vec3(0, 0, offset + pOffset), yRot)); // Front
        if(isEmpty(AABB.ofSize(pos.add(wOffset), pWidth, 1.0E-6D, pWidth)))
            return pos;

        pos = position().add(rotateY(new Vec3(0, 0, -offset - pOffset), yRot)); // Back
        if(isEmpty(AABB.ofSize(pos.add(wOffset), pWidth, 1.0E-6D, pWidth)))
            return pos;

        return super.getDismountLocationForPassenger(passenger);
    }

    public boolean isEmpty(AABB aabb) {
        return BlockPos.betweenClosedStream(aabb).noneMatch(pos -> {
            BlockState state = level().getBlockState(pos);
            return !state.isAir() && state.isSuffocating(level(), pos) &&
                    Shapes.joinIsNotEmpty(state.getCollisionShape(level(), pos).move(pos.getX(), pos.getY(), pos.getZ()), Shapes.create(aabb), BooleanOp.AND);
        });
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else if (!this.level().isClientSide && !this.isRemoved()) {
            level().playSound(null, blockPosition(), SoundEvents.WOOD_BREAK, SoundSource.MASTER);
            double w = getBbWidth();
            double h = getBbHeight();
            ((ServerLevel)level()).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.SPRUCE_PLANKS.defaultBlockState()),
                    getX(), getY(), getZ(), 100, w, h, w, 0);
            this.setHealth(this.getHealth() - amount);
            this.markHurt();
            this.gameEvent(GameEvent.ENTITY_DAMAGE, source.getEntity());

            if (source.getEntity() instanceof Player player) {
                if (this.getOwner().equals(player.getUUID())) {
                    if (getHealth() <= 0) {
                        onDestroyed(true);
                    }
                } else {
                    if (getHealth() <= 0) {
                        onDestroyed(false);
                    }
                }
            }

            return true;
        }

        if(level().isClientSide || isRemoved())
            return true;

        return true;
    }

    protected void onDestroyed(boolean dropItem) {
        if(level().isClientSide)
            return;

        ejectPassengers();
        discard();

        if(!level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS))
            return;

        if(dropItem) {
            ItemStack pickResult = getPickResult();
            if(pickResult != null)
                spawnAtLocation(pickResult);
        } else {
            spawnAtLocation(new ItemStack(Items.IRON_INGOT, RANDOM.nextInt(10)));
            spawnAtLocation(new ItemStack(Items.GLASS, RANDOM.nextInt(3)));
        }
    }

    private static final EntityDataAccessor<Optional<UUID>> DATA_OWNER = SynchedEntityData.defineId(AbstractVehicle.class, EntityDataSerializers.OPTIONAL_UUID);
    @Nullable
    public UUID getOwner() {
        return this.owner;
    }

    public void setOwner(@Nullable UUID pUuid) {
        this.owner = pUuid;
    }

    public static final EntityDataAccessor<Integer> DATA_COLOR = SynchedEntityData.defineId(AbstractVehicle.class, EntityDataSerializers.INT);
    public DyeColor getColor() {
        return DyeColor.byId(this.entityData.get(DATA_COLOR));
    }
    public void setColor(DyeColor p_30398_) {
        this.entityData.set(DATA_COLOR, p_30398_.getId());
    }

    public static final EntityDataAccessor<Boolean> LOCKED = SynchedEntityData.defineId(AbstractVehicle.class, EntityDataSerializers.BOOLEAN);
    public boolean isLocked() {
        return this.entityData.get(LOCKED);
    }
    public void setLocked(boolean locked) {
        this.entityData.set(LOCKED, locked);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        if (this.getOwner() != null) {
            tag.putUUID("owner", this.getOwner());
        }

        ListTag animals = new ListTag();

        tag.put("animals", animals);
        tag.putByte("color", (byte)this.getColor().getId());
        tag.putBoolean("locked", this.isLocked());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        if (tag.contains("color", 99)) {
            this.setColor(DyeColor.byId(tag.getInt("color")));
        }

        UUID uuid;
        if (tag.hasUUID("owner")) {
            uuid = tag.getUUID("owner");
        } else {
            String s = tag.getString("owner");
            uuid = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), s);
        }

        if (uuid != null) {
            this.setOwner(uuid);
        }

        if (tag.contains("locked")) {
            this.setLocked(tag.getBoolean("locked"));
        }
    }

    public float getHealth() {
        return entityData.get(DATA_HEALTH);
    }

    protected void setHealth(float health) {
        entityData.set(DATA_HEALTH, Mth.clamp(health, 0, maxHealth));
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(DATA_TYPE, 0);
        entityData.define(DATA_HEALTH, (float)maxHealth);
        this.entityData.define(DATA_COLOR, DyeColor.WHITE.getId());
        this.entityData.define(DATA_OWNER, Optional.empty());
        this.entityData.define(LOCKED, false);
    }

    @Override
    public boolean isControlledByLocalInstance() {
        return isEffectiveAi();
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        return !getType().is(EntityTypeTags.FALL_DAMAGE_IMMUNE);
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

}
