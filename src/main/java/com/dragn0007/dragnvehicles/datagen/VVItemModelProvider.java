package com.dragn0007.dragnvehicles.datagen;

import com.dragn0007.dragnvehicles.ValiantVehiclesMain;
import com.dragn0007.dragnvehicles.item.VVItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class VVItemModelProvider extends ItemModelProvider {
    public VVItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ValiantVehiclesMain.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(VVItems.CAR_BODY);
        simpleItem(VVItems.CLASSIC_BODY);
        simpleItem(VVItems.TRUCK_BODY);
        simpleItem(VVItems.SUV_BODY);
        simpleItem(VVItems.SPORT_CAR_BODY);
        simpleItem(VVItems.MOTORCYCLE_BODY);
        simpleItem(VVItems.WHEEL);
        simpleItem(VVItems.SPORTS_WHEEL);
        simpleItem(VVItems.ENGINE);
        simpleItem(VVItems.SPORTS_ENGINE);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(ValiantVehiclesMain.MODID,"item/" + item.getId().getPath()));
    }
}