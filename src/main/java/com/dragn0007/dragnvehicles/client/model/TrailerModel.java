package com.dragn0007.dragnvehicles.client.model;

import com.dragn0007.dragnvehicles.ValiantVehiclesMain;
import com.dragn0007.dragnvehicles.vehicle.Trailer;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class TrailerModel<T extends Trailer> extends DefaultedEntityGeoModel<T> {

    private final String name;

    public TrailerModel(String name) {
        super(new ResourceLocation(ValiantVehiclesMain.MODID, name), false);
        this.name = name;
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        return ValiantVehiclesMain.id("textures/entity/" + name + "/" + animatable.getColor() + ".png");
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