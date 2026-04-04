package com.dragn0007.dragnvehicles.client.model;

import com.dragn0007.dragnvehicles.ValiantVehiclesMain;
import com.dragn0007.dragnvehicles.vehicle.Trailer;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class LivestockTrailerModel<T extends Trailer> extends DefaultedEntityGeoModel<T> {

    public LivestockTrailerModel() {
        super(new ResourceLocation(ValiantVehiclesMain.MODID, "livestock_trailer"), false);
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        return ValiantVehiclesMain.id("textures/entity/livestock_trailer/silver.png");
    }

    @Override
    public void setCustomAnimations(T animatable, long instanceId, AnimationState<T> state) {
        super.setCustomAnimations(animatable, instanceId, state);

//        AnimationProcessor<T> processor = getAnimationProcessor();
//        float partialTick = (float)state.animationTick - (int)state.animationTick;
//        float rot = animatable.getWheelRot(partialTick);
//
//        processor.getBone("front_wheels").setRotX(-rot);
//        processor.getBone("back_wheels").setRotX(-rot);
    }


}