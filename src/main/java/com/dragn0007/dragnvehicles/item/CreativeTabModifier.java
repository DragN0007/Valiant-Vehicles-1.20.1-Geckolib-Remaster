package com.dragn0007.dragnvehicles.item;

import com.dragn0007.dragnvehicles.ValiantVehiclesMain;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;

@Mod.EventBusSubscriber(modid = ValiantVehiclesMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CreativeTabModifier {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ValiantVehiclesMain.MODID);

       @SubscribeEvent
    public static void buildContents(BuildCreativeModeTabContentsEvent event) {
           if(event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
               event.accept(VVItems.CAR_SPAWN_EGG);
               event.accept(VVItems.CLASSIC_SPAWN_EGG);
               event.accept(VVItems.SPORTCAR_SPAWN_EGG);
               event.accept(VVItems.SUV_SPAWN_EGG);
               event.accept(VVItems.TRUCK_SPAWN_EGG);
//               event.accept(VVItems.MOTORCYCLE_SPAWN_EGG);

               event.accept(VVItems.CAR_BODY);
               event.accept(VVItems.CLASSIC_BODY);
               event.accept(VVItems.MOTORCYCLE_BODY);
               event.accept(VVItems.SPORT_CAR_BODY);
               event.accept(VVItems.SUV_BODY);
               event.accept(VVItems.TRUCK_BODY);

               event.accept(VVItems.WHEEL);
               event.accept(VVItems.SPORTS_WHEEL);
               event.accept(VVItems.ENGINE);
               event.accept(VVItems.SPORTS_ENGINE);
           }
       }

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
