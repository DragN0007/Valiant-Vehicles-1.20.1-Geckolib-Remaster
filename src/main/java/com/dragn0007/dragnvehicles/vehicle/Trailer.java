package com.dragn0007.dragnvehicles.vehicle;

import com.dragn0007.dragnvehicles.menus.LivestockTrailerMenu;
import com.dragn0007.dragnvehicles.vehicle.base.AbstractVehicle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class Trailer extends Entity implements GeoEntity, MenuProvider {

    protected final AnimatableInstanceCache animCache = GeckoLibUtil.createInstanceCache(this);

    private Hitchable vehicle;
    private final double length;
    private final Vec3[] riders;

    private final NonNullList<ItemStack> inventory;
    private final ItemStackHandler itemHandler;
    private final LazyOptional<IItemHandlerModifiable> optionalItemHandler;

    protected int lerpSteps = 0;
    protected double lerpX = 0;
    protected double lerpY = 0;
    protected double lerpZ = 0;
    protected double lerpXRot = 0;
    protected double lerpYRot = 0;

    public Trailer(EntityType<?> type, Level level, double length, Vec3[] riders, int capacity) {
        super(type, level);
        this.length = length;
        this.riders = riders;
        this.inventory = NonNullList.withSize(capacity, ItemStack.EMPTY);
        this.itemHandler = new ItemStackHandler(inventory);
        this.optionalItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if(!player.level().isClientSide && vehicle == null) {
            Hitchable vehicle = level().getEntitiesOfClass(AbstractVehicle.class, this.getBoundingBox().inflate(10)).stream()
                    .filter(v -> v instanceof Hitchable)
                    .map(v -> (Hitchable)v)
                    .findFirst().orElse(null);

            if(vehicle == null)
                return super.interact(player, hand);

            this.vehicle = vehicle;
        }

        if(tryMountMob(player))
            return InteractionResult.sidedSuccess(level().isClientSide);

        if(isAlive() && !level().isClientSide && player.isSecondaryUseActive()) {
            NetworkHooks.openScreen((ServerPlayer)player, this, buf -> buf.writeInt(getId()));
            return InteractionResult.CONSUME;
        }

        return super.interact(player, hand);
    }

    @Override
    public void tick() {
        super.tick();
        tickLerp();

        if(level().isClientSide() || vehicle == null)
            return;

        Vec3 mountPos = rotateY(vehicle.getHitchPoint(), vehicle.getYRot()).add(vehicle.position());

        float rot = -(float)Mth.atan2(mountPos.x() - getX(), mountPos.z() - getZ()) * Mth.RAD_TO_DEG;

        setYRot(rot);

        Vec3 front = rotateY(new Vec3(0, 0, length), rot).add(position());
        Vec3 move = mountPos.subtract(front).multiply(1, 0, 1);

        if (!isNoGravity())
            move = move.add(0, -0.08D, 0);

        setDeltaMovement(move);
        move(MoverType.SELF, move);
        setXRot(rotlerp(getXRot(), calculateTargetXRot(), 3)); // 3 is just a magic scaling number for smoothing.
    }

    protected void tickLerp() {
        if(isControlledByLocalInstance()) {
            lerpSteps = 0;
            syncPacketPositionCodec(getX(), getY(), getZ());
        }

        if(lerpSteps > 0) {
            double f = 1.0D / lerpSteps;
            double x = Mth.lerp(f, getX(), lerpX);
            double y = Mth.lerp(f, getY(), lerpY);
            double z = Mth.lerp(f, getZ(), lerpZ);

            float xRot = (float)Mth.lerp(f, getXRot(), lerpXRot);
            float yRot = (float)Mth.wrapDegrees(lerpYRot - getYRot()) / lerpSteps + getYRot(); // Doing a normal lerp here causes "snapping" at -180/180

            --lerpSteps;
            setPos(x, y, z);
            setRot(yRot, xRot);
        }
    }

    private boolean tryMountMob(Player player) {
        Mob mob = level().getEntitiesOfClass(Mob.class, new AABB(
                        player.getX()-7, player.getY()-7, player.getZ()-7,
                        player.getX()+7, player.getY()+7, player.getZ()+7
                ), h -> h.getLeashHolder() == player).stream()
                .findFirst().orElse(null);

        if(mob != null && !level().isClientSide && canAddPassenger(mob))
            mob.startRiding(this);

        return mob != null;
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return passenger instanceof LivingEntity && getPassengers().size() < riders.length;
    }

    @Override
    protected void positionRider(Entity passenger, MoveFunction callback) {
        Vec3 local = riders[getPassengerIndex(passenger)];

        local = rotateX(local, getXRot());
        local = rotateY(local, getYRot());
        local = local.subtract(0, 0.7D, 0); // Offset player positions because the sit anim makes them float.

        callback.accept(passenger, getX() + local.x, getY() + local.y, getZ() + local.z);
    }

    private int getPassengerIndex(Entity passenger) {
        int i = 0;
        for(Entity ent : getPassengers()) {
            if(ent == passenger)
                break;
            i++;
        }
        return i;
    }


    public double getLength() {
        return length;
    }

    protected float calculateTargetXRot() {
        Vec3 point = rotateX(vehicle.getHitchPoint(), vehicle.getXRot());
        double xDiff = (point.y + vehicle.position().y) - getY();
        float f = (float) Mth.clamp(xDiff / length, -1, 1);

        return f * -45;
    }

    public float getXRot(float partialTick) {
        return Mth.lerp(partialTick, xRotO, getXRot());
    }

    protected float rotlerp(float angle, float target, float max) {
        float d = Mth.wrapDegrees(target - angle);
        if(d > max)
            d = max;
        if(d < -max)
            d = -max;

        return angle + d;
    }

    protected Vec3 rotateY(Vec3 pos, float angle) {
        return rotateY(pos.x, pos.y, pos.z, angle);
    }

    protected Vec3 rotateX(Vec3 pos, float angle) {
        return rotateX(pos.x, pos.y, pos.z, angle);
    }

    protected Vec3 rotateY(double x, double y, double z, float angle) {
        float a = angle * Mth.DEG_TO_RAD;
        float sin = Mth.sin(a);
        float cos = Mth.cos(a);

        return new Vec3(x * cos - z * sin, y, z * cos + x * sin);
    }

    protected Vec3 rotateX(double x, double y, double z, float angle) {
        float a = angle * Mth.DEG_TO_RAD;
        float sin = Mth.sin(a);
        float cos = Mth.cos(a);

        return new Vec3(x, y * cos - z * sin, z * cos + y * sin);
    }

    @Override
    public void lerpTo(double x, double y, double z, float yRot, float xRot, int steps, boolean teleport) {
        lerpX = x;
        lerpY = y;
        lerpZ = z;
        lerpYRot = yRot;
        lerpXRot = xRot;
        lerpSteps = steps;
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {}

    @Override
    public float getStepHeight() {
        return 1.0F;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        ContainerHelper.saveAllItems(nbt, inventory, false);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        ContainerHelper.loadAllItems(nbt, inventory);
    }

    public ItemStackHandler getItems() {
        return itemHandler;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == ForgeCapabilities.ITEM_HANDLER ? optionalItemHandler.cast() : super.getCapability(cap, side);
    }

    @Override
    public void registerControllers(ControllerRegistrar registrar) {}

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animCache;
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new LivestockTrailerMenu(id, inventory, this);
    }





}
