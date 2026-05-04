package com.dragn0007.dragnvehicles.vehicle;

import com.dragn0007.dragnvehicles.item.VVItems;
import com.dragn0007.dragnvehicles.menus.LivestockTrailerMenu;
import com.dragn0007.dragnvehicles.util.VVTags;
import com.dragn0007.dragnvehicles.vehicle.base.AbstractVehicle;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class HorseTrailer extends Trailer implements GeoEntity, MenuProvider {

    protected final AnimatableInstanceCache animCache = GeckoLibUtil.createInstanceCache(this);

    private TruckHitchable vehicle;

    public HorseTrailer(EntityType<?> type, Level level, double length, Vec3[] riders, int capacity) {
        super(type, level, length, riders, capacity);
    }

    @Nullable
    @Override
    public ItemStack getPickResult() {
        return VVItems.HORSE_TRAILER_SPAWN_EGG.get().getDefaultInstance();
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        Item item = stack.getItem();

        if(!player.level().isClientSide && vehicle == null) {
            TruckHitchable vehicle = level().getEntitiesOfClass(AbstractVehicle.class, this.getBoundingBox().inflate(10)).stream()
                    .filter(v -> v instanceof TruckHitchable) //this trailer can only be hitched to pickup trucks
                    .map(v -> (TruckHitchable)v)
                    .findFirst().orElse(null);

            if(vehicle == null)
                return super.interact(player, hand);

            this.vehicle = vehicle;
        }

        if (player.isShiftKeyDown()) {
            if (item instanceof DyeItem) {
                DyeItem dyeitem = (DyeItem) item;
                DyeColor dyecolor = dyeitem.getDyeColor();
                if ((dyecolor != this.getColor())) {
                    this.setColor(dyecolor);
                    if (!player.getAbilities().instabuild) {
                        stack.shrink(1);
                    }
                    return InteractionResult.SUCCESS;
                }
            }
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
    boolean tryMountMob(Player player) {
        Mob mob = level().getEntitiesOfClass(Mob.class, new AABB(
                        player.getX()-7, player.getY()-7, player.getZ()-7,
                        player.getX()+7, player.getY()+7, player.getZ()+7
                ), h -> h.getLeashHolder() == player
                        && h.getType().is(VVTags.Entity_Types.HORSES)
        ).stream().findFirst().orElse(null);

        if(mob != null && !level().isClientSide && canAddPassenger(mob))
            mob.startRiding(this);

        return mob != null;
    }

    @Override
    public void registerControllers(ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, vehicle -> {
            if(getDeltaMovement().horizontalDistance() > 0.01F)
                return vehicle.setAndContinue(ANIM_MOVE);
            return PlayState.STOP;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animCache;
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new LivestockTrailerMenu(id, inventory, this);
    }
}
