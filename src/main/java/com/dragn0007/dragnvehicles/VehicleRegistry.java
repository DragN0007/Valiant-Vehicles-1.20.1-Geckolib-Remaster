package com.dragn0007.dragnvehicles;

import com.dragn0007.dragnvehicles.vehicle.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.dragn0007.dragnvehicles.ValiantVehiclesMain.MODID;

public class VehicleRegistry {

    private static final Vec3[] LIVESTOCK_RIDERS =  new Vec3[]{
            new Vec3(0.0D, 1.25D, 2.0D),
            new Vec3(0.0D, 1.25D, 1.0D),
            new Vec3(0.0D, 1.25D, 0.0D),
            new Vec3(0.0D, 1.25D, -1.0D),
            new Vec3(0.0D, 1.25D, -2.0D),
            new Vec3(0.0D, 1.25D, -3.0D),
    };

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);

    public static final RegistryObject<EntityType<Car>> CAR = ENTITY_TYPES.register("car",
            () -> EntityType.Builder.of(Car::new, MobCategory.MISC).sized(2.5f, 1.9f).build(new ResourceLocation(MODID, "car").toString()));

    public static final RegistryObject<EntityType<Classic>> CLASSIC = ENTITY_TYPES.register("classic",
            () -> EntityType.Builder.of(Classic::new, MobCategory.MISC).sized(2.5f, 1.6f).build(new ResourceLocation(MODID, "classic").toString()));

    public static final RegistryObject<EntityType<Pickup>> TRUCK = ENTITY_TYPES.register("truck",
            () -> EntityType.Builder.of(Pickup::new, MobCategory.MISC).sized(2.5f, 2.2f).build(new ResourceLocation(MODID, "truck").toString()));

    public static final RegistryObject<EntityType<SUV>> SUV = ENTITY_TYPES.register("suv",
            () -> EntityType.Builder.of(SUV::new, MobCategory.MISC).sized(2.5f, 2.2f).build(new ResourceLocation(MODID, "suv").toString()));

    public static final RegistryObject<EntityType<Sportcar>> SPORT_CAR = ENTITY_TYPES.register("sport_car",
            () -> EntityType.Builder.of(Sportcar::new, MobCategory.MISC).sized(2.5f, 1.6f).build(new ResourceLocation(MODID, "sport_car").toString()));

    public static final RegistryObject<EntityType<Motorcycle>> MOTORCYCLE = ENTITY_TYPES.register("motorcycle",
            () -> EntityType.Builder.of(Motorcycle::new, MobCategory.MISC).sized(0.8f, 1.2f).build(new ResourceLocation(MODID, "motorcycle").toString()));

    public static final RegistryObject<EntityType<Trailer>> LIVESTOCK_TRAILER = ENTITY_TYPES.register("livestock_trailer",
            () -> EntityType.Builder.<Trailer>of((type, level) -> new Trailer(type, level, 5.1D, LIVESTOCK_RIDERS, 9), MobCategory.MISC).sized(2.5f, 2.2f).build(new ResourceLocation(MODID, "livestock_trailer").toString()));
}
