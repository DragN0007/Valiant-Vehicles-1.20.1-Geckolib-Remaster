package com.dragn0007.dragnvehicles;

import com.dragn0007.dragnvehicles.common.network.VVPackets;
import com.dragn0007.dragnvehicles.item.VVItems;
import com.dragn0007.dragnvehicles.util.ValiantVehiclesCommonConfig;
import com.dragn0007.dragnvehicles.vehicle.VehicleRegistry;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import static com.dragn0007.dragnvehicles.ValiantVehiclesMain.MODID;

@Mod(MODID)
public class ValiantVehiclesMain
{
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String MODID = "dragnvehicles";

    public ValiantVehiclesMain()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        VVItems.ITEMS.register(eventBus);
        VehicleRegistry.ENTITY_TYPES.register(eventBus);
        VVPackets.register();

        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ValiantVehiclesCommonConfig.SPEC, "valiant-vehicles-common.toml");
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MODID, path);
    }
}