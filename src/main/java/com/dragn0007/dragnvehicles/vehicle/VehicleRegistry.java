package com.dragn0007.dragnvehicles.vehicle;

import com.dragn0007.dragnvehicles.client.model.PickupRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.dragn0007.dragnvehicles.ValiantVehiclesMain.MODID;

public class VehicleRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);

    public static final RegistryObject<EntityType<Car>> CAR = ENTITY_TYPES.register("car",
            () -> EntityType.Builder.of(Car::new,
                            MobCategory.MISC)
                    .sized(2.5f, 1.9f)
                    .build(new ResourceLocation(MODID, "car").toString()));

    public static final RegistryObject<EntityType<Classic>> CLASSIC = ENTITY_TYPES.register("classic",
            () -> EntityType.Builder.of(Classic::new,
                            MobCategory.MISC)
                    .sized(2.5f, 1.6f)
                    .build(new ResourceLocation(MODID, "classic").toString()));

    public static final RegistryObject<EntityType<Pickup>> TRUCK = ENTITY_TYPES.register("truck",
            () -> EntityType.Builder.of(Pickup::new,
                            MobCategory.MISC)
                    .sized(2.5f, 2.2f)
                    .build(new ResourceLocation(MODID, "truck").toString()));

    public static final RegistryObject<EntityType<SUV>> SUV = ENTITY_TYPES.register("suv",
            () -> EntityType.Builder.of(SUV::new,
                            MobCategory.MISC)
                    .sized(2.5f, 2.2f)
                    .build(new ResourceLocation(MODID, "suv").toString()));

    public static final RegistryObject<EntityType<Sportcar>> SPORT_CAR = ENTITY_TYPES.register("sport_car",
            () -> EntityType.Builder.of(Sportcar::new,
                            MobCategory.MISC)
                    .sized(2.5f, 1.6f)
                    .build(new ResourceLocation(MODID, "sport_car").toString()));
}
