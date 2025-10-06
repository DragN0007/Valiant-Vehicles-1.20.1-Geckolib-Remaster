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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public class Car extends AbstractInventoryVehicle implements ContainerListener {

    public static final Vec3[] RIDERS = new Vec3[] {
            new Vec3(0.5D, 0.58D, 0.2D),
            new Vec3(-0.5D, 0.58D, 0.2D),
            new Vec3(0.5D, 0.58D, -0.6D),
            new Vec3(-0.5D, 0.58D, -0.6D)
    };

    public Car(EntityType<? extends AbstractVehicle> type, Level level) {
        super(type, level,
                ValiantVehiclesCommonConfig.CAR_SPEED_MULT.get(),
                ValiantVehiclesCommonConfig.CAR_SPEED_ACC.get(),
                ValiantVehiclesCommonConfig.CAR_SPEED_TURN.get(),
                ValiantVehiclesCommonConfig.CAR_HEALTH.get(),
                1.25D,
                1.25D,
                RIDERS);
        this.createInventory();
    }

    public ItemStack getPickResult() {
        return VVItems.CAR_SPAWN_EGG.get().getDefaultInstance();
    }

    @Override
    @NotNull
    public InteractionResult interact(Player player, InteractionHand hand) {
        if(player.isShiftKeyDown()) {
            if(!this.level().isClientSide) {
                NetworkHooks.openScreen((ServerPlayer) player, new SimpleMenuProvider((containerId, inventory, serverPlayer) ->
                        ChestMenu.threeRows(containerId, inventory, this.inventory), this.getDisplayName()));
            }
        }
        return super.interact(player, hand);
    }

    @Override
    public void containerChanged(Container container) {

    }
}
