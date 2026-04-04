package com.dragn0007.dragnvehicles.vehicle;

import com.dragn0007.dragnvehicles.item.VVItems;
import com.dragn0007.dragnvehicles.util.ValiantVehiclesCommonConfig;
import com.dragn0007.dragnvehicles.vehicle.base.AbstractInventoryVehicle;
import com.dragn0007.dragnvehicles.vehicle.base.AbstractVehicle;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public class SUV extends AbstractInventoryVehicle implements ContainerListener, Hitchable {

    public static final Vec3[] RIDERS = new Vec3[] {
            new Vec3(0.5D, 0.65D, 0.2D),
            new Vec3(-0.5D, 0.65D, 0.2D),
            new Vec3(0.5D, 0.65D, -0.6D),
            new Vec3(-0.5D, 0.65D, -0.6D)
    };

    public SUV(EntityType<? extends AbstractVehicle> type, Level level) {
        super(type, level,
                ValiantVehiclesCommonConfig.SUV_SPEED_MULT.get(),
                ValiantVehiclesCommonConfig.SUV_SPEED_ACC.get(),
                ValiantVehiclesCommonConfig.SUV_SPEED_TURN.get(),
                ValiantVehiclesCommonConfig.SUV_HEALTH.get(),
                1.25D,
                1.25D,
                RIDERS);
        this.createInventory();
    }

    public ItemStack getPickResult() {
        return VVItems.SUV_SPAWN_EGG.get().getDefaultInstance();
    }

    @Override
    protected void createInventory() {
        this.inventory = new SimpleContainer(36);
        this.inventory.addListener(this);
        this.itemHandler = LazyOptional.of(() -> new InvWrapper(this.inventory));
    }

    @Override
    @NotNull
    public InteractionResult interact(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        Item item = stack.getItem();
        if(player.isShiftKeyDown() && !(item instanceof DyeItem) && (item != VVItems.CAR_KEY.get())) {
//            if ((this.isLocked() && this.getOwner().equals(player.getUUID())) || (!this.isLocked())) {
                if (!this.level().isClientSide) {
                    NetworkHooks.openScreen((ServerPlayer) player, new SimpleMenuProvider((containerId, inventory, serverPlayer) ->
                            new ChestMenu(MenuType.GENERIC_9x4, containerId, inventory, this.inventory, 4), this.getDisplayName()));
                }
//            }
        }
        return super.interact(player, hand);
    }

    @Override
    public void containerChanged(Container container) {}


    @Override
    public Vec3 getHitchPoint() {
        return new Vec3(0, 0, -2.55D);
    }

}
