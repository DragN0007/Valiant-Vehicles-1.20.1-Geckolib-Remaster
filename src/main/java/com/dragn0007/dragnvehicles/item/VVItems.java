package com.dragn0007.dragnvehicles.item;

import com.dragn0007.dragnvehicles.ValiantVehiclesMain;
import com.dragn0007.dragnvehicles.item.custom.VehicleItem;
import com.dragn0007.dragnvehicles.VehicleRegistry;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class VVItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ValiantVehiclesMain.MODID);

    public static final RegistryObject<Item> CAR_SPAWN_EGG = ITEMS.register("car",
            () -> new VehicleItem(VehicleRegistry.CAR::get, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> CLASSIC_SPAWN_EGG = ITEMS.register("classic",
            () -> new VehicleItem(VehicleRegistry.CLASSIC::get, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> TRUCK_SPAWN_EGG = ITEMS.register("truck",
            () -> new VehicleItem(VehicleRegistry.TRUCK::get, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> SUV_SPAWN_EGG = ITEMS.register("suv",
            () -> new VehicleItem(VehicleRegistry.SUV::get, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> SPORTCAR_SPAWN_EGG = ITEMS.register("sport_car",
            () -> new VehicleItem(VehicleRegistry.SPORT_CAR::get, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> MOTORCYCLE_SPAWN_EGG = ITEMS.register("motorcycle",
            () -> new VehicleItem(VehicleRegistry.MOTORCYCLE::get, new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> CAR_BODY = ITEMS.register("car_body",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CLASSIC_BODY = ITEMS.register("classic_body",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TRUCK_BODY = ITEMS.register("truck_body",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SUV_BODY = ITEMS.register("suv_body",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SPORT_CAR_BODY = ITEMS.register("sport_car_body",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MOTORCYCLE_BODY = ITEMS.register("motorcycle_body",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WHEEL = ITEMS.register("wheel",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SPORTS_WHEEL = ITEMS.register("sports_wheel",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ENGINE = ITEMS.register("engine",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SPORTS_ENGINE = ITEMS.register("sports_engine",
            () -> new Item(new Item.Properties()));
}
