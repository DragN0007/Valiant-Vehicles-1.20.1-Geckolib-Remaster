package com.dragn0007.dragnvehicles.client.ui;

import com.dragn0007.dragnvehicles.ValiantVehiclesMain;
import com.dragn0007.dragnvehicles.util.ValiantVehiclesClientConfig;
import com.dragn0007.dragnvehicles.util.ValiantVehiclesCommonConfig;
import com.dragn0007.dragnvehicles.vehicle.base.AbstractInventoryVehicle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ValiantVehiclesMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class FuelHUD {

    @SubscribeEvent
    public static void onFMLClientSetupEvent(RegisterGuiOverlaysEvent event) {

        event.registerAbove(VanillaGuiOverlay.HOTBAR.id(), "fuel_hud", (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
            Minecraft minecraft = Minecraft.getInstance();
            LivingEntity livingEntity = (LivingEntity) minecraft.getCameraEntity();
            int maxfuelBurnTime = ValiantVehiclesCommonConfig.FUEL_BURN_TIME.get();

            if(!minecraft.options.hideGui && livingEntity.getVehicle() instanceof AbstractInventoryVehicle vehicle) {
                if (vehicle.getControllingPassenger() instanceof LocalPlayer && livingEntity instanceof LocalPlayer) {
                    int x = (screenWidth / 2) + ValiantVehiclesClientConfig.HUD_X.get();
                    int y = screenHeight - ValiantVehiclesClientConfig.HUD_Y.get();

                    if (vehicle.fuelBurnTime > (maxfuelBurnTime * 0.80)) {
                        ResourceLocation location = new ResourceLocation(ValiantVehiclesMain.MODID, "textures/gui/100_fuel.png");
                        guiGraphics.blit(location, x, y, 0, 0, 32, 50);
                    } else if ((vehicle.fuelBurnTime <= (maxfuelBurnTime * 0.80) && (vehicle.fuelBurnTime > (maxfuelBurnTime * 0.60)))) {
                        ResourceLocation location = new ResourceLocation(ValiantVehiclesMain.MODID, "textures/gui/80_fuel.png");
                        guiGraphics.blit(location, x, y, 0, 0, 32, 50);
                    } else if ((vehicle.fuelBurnTime < (maxfuelBurnTime * 0.60) && (vehicle.fuelBurnTime > (maxfuelBurnTime * 0.50)))) {
                        ResourceLocation location = new ResourceLocation(ValiantVehiclesMain.MODID, "textures/gui/60_fuel.png");
                        guiGraphics.blit(location, x, y, 0, 0, 32, 50);
                    } else if ((vehicle.fuelBurnTime < (maxfuelBurnTime * 0.50) && (vehicle.fuelBurnTime > (maxfuelBurnTime * 0.30)))) {
                        ResourceLocation location = new ResourceLocation(ValiantVehiclesMain.MODID, "textures/gui/50_fuel.png");
                        guiGraphics.blit(location, x, y, 0, 0, 32, 50);
                    } else if ((vehicle.fuelBurnTime < (maxfuelBurnTime * 0.30) && (vehicle.fuelBurnTime > 0))) {
                        ResourceLocation location = new ResourceLocation(ValiantVehiclesMain.MODID, "textures/gui/30_fuel.png");
                        guiGraphics.blit(location, x, y, 0, 0, 32, 50);
                    } else if (vehicle.fuelBurnTime <= 0) {
                        ResourceLocation location = new ResourceLocation(ValiantVehiclesMain.MODID, "textures/gui/0_fuel.png");
                        guiGraphics.blit(location, x, y, 0, 0, 32, 50);
                    }

                    gui.setupOverlayRenderState(true, false);


                }
            }
        });

    }
}