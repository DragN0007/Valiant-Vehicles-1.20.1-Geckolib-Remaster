package com.dragn0007.dragnvehicles.client;

import com.dragn0007.dragnvehicles.ValiantVehiclesMain;
import com.dragn0007.dragnvehicles.VehicleRegistry;
import com.dragn0007.dragnvehicles.client.model.*;
import com.dragn0007.dragnvehicles.client.screens.LivestockTrailerScreen;
import com.dragn0007.dragnvehicles.menus.MenuRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;


@Mod.EventBusSubscriber(modid = ValiantVehiclesMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class VVClientEvent {

    @SubscribeEvent
    public static void clientSetupEvent(FMLClientSetupEvent event) {
        EntityRenderers.register(VehicleRegistry.CAR.get(), ctx -> new VehicleRenderer<>(ctx, ValiantVehiclesMain.id("car")));
        EntityRenderers.register(VehicleRegistry.CLASSIC.get(), ctx -> new VehicleRenderer<>(ctx, ValiantVehiclesMain.id("classic")));
        EntityRenderers.register(VehicleRegistry.TRUCK.get(), ctx -> new VehicleRenderer<>(ctx, ValiantVehiclesMain.id("pickup")));
        EntityRenderers.register(VehicleRegistry.SUV.get(), ctx -> new VehicleRenderer<>(ctx, ValiantVehiclesMain.id("suv")));
        EntityRenderers.register(VehicleRegistry.SPORT_CAR.get(), ctx -> new VehicleRenderer<>(ctx, ValiantVehiclesMain.id("sportcar")));

        EntityRenderers.register(VehicleRegistry.MOTORCYCLE.get(), ctx -> new VehicleRenderer<>(ctx, new MotorcycleModel<>()));
        EntityRenderers.register(VehicleRegistry.LIVESTOCK_TRAILER.get(), ctx -> new TrailerRenderer<>(ctx, new TrailerModel<>("livestock_trailer")));

        MenuScreens.register(MenuRegistry.LIVESTOCK_TRAILER.get(), LivestockTrailerScreen::new);
    }

}




