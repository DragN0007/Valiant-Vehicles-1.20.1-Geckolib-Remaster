package com.dragn0007.dragnvehicles.util;

import com.dragn0007.dragnvehicles.ValiantVehiclesMain;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

public class VVTags {

    public static class Entity_Types {
        public static final TagKey<EntityType<?>> CANNOT_RIDE_VEHICLE = tag("cannot_ride_vehicle");
        public static final TagKey<EntityType<?>> HORSES = tag("horses");

        public static TagKey<EntityType<?>> forgeTag(String name) {
            return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("forge", name));
        }
        public static TagKey<EntityType<?>> tag(String name) {
            return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(ValiantVehiclesMain.MODID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> FUEL = tag("fuel");

        public static TagKey<Item> forgeTag (String name) {
            return ItemTags.create(new ResourceLocation("forge", name));
        }
        public static TagKey<Item> tag (String name) {
            return ItemTags.create(new ResourceLocation(ValiantVehiclesMain.MODID, name));
        }
    }

}
