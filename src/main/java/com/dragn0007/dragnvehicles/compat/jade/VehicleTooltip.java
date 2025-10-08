package com.dragn0007.dragnvehicles.compat.jade;

import com.dragn0007.dragnvehicles.ValiantVehiclesMain;
import com.dragn0007.dragnvehicles.vehicle.base.AbstractVehicle;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.util.CommonProxy;

import java.util.UUID;

public class VehicleTooltip implements IEntityComponentProvider {

    public VehicleTooltip() {
    }

    @Override
    public void appendTooltip(ITooltip tooltip, EntityAccessor entityAccessor, IPluginConfig iPluginConfig) {
        AbstractVehicle entity = (AbstractVehicle)entityAccessor.getEntity();
        if (entity.isLocked()) {
            tooltip.add(Component.translatable("tooltip.dragnvehicles.jade.locked.tooltip"));
        } else {
            tooltip.add(Component.translatable("tooltip.dragnvehicles.jade.unlocked.tooltip"));
        }

        if (entity.getOwner() != null) {
            tooltip.add(Component.literal("Owner UUID: " + entity.getOwner()));
        } else {
            tooltip.add(Component.translatable("tooltip.dragnvehicles.jade.no_owner.tooltip"));
        }
    }

    @Override
    public ResourceLocation getUid() {
        return new ResourceLocation(ValiantVehiclesMain.MODID, "vehicle_tooltips");
    }

}
