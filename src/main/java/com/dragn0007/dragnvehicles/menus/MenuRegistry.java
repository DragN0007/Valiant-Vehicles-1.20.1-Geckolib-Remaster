package com.dragn0007.dragnvehicles.menus;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.dragn0007.dragnvehicles.ValiantVehiclesMain.MODID;

public class MenuRegistry {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);

    public static final RegistryObject<MenuType<LivestockTrailerMenu>> LIVESTOCK_TRAILER = MENU_TYPES.register("livestock_trailer", () -> IForgeMenuType.create(LivestockTrailerMenu::new));
}
