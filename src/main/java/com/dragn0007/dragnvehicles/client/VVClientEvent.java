package com.dragn0007.dragnvehicles.client;

import com.dragn0007.dragnvehicles.ValiantVehiclesMain;
import com.dragn0007.dragnvehicles.client.model.*;
import com.dragn0007.dragnvehicles.VehicleRegistry;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;


@Mod.EventBusSubscriber(modid = ValiantVehiclesMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class VVClientEvent {
    @SubscribeEvent
    public static void clientSetupEvent(FMLClientSetupEvent event) {
        EntityRenderers.register(VehicleRegistry.CAR.get(), c -> new CarRenderer<>(c, "car"));
        EntityRenderers.register(VehicleRegistry.CLASSIC.get(), c -> new ClassicRenderer<>(c, "classic"));
        EntityRenderers.register(VehicleRegistry.TRUCK.get(), c -> new PickupRenderer<>(c, "pickup"));
        EntityRenderers.register(VehicleRegistry.SUV.get(), c -> new SUVRenderer<>(c, "suv"));
        EntityRenderers.register(VehicleRegistry.SPORT_CAR.get(), c -> new SportcarRenderer<>(c, "sportcar"));
        EntityRenderers.register(VehicleRegistry.MOTORCYCLE.get(), c -> new MotorcycleRenderer<>(c, "motorcycle"));
    }

}




