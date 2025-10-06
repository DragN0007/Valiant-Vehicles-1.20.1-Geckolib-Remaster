package com.dragn0007.dragnvehicles.client;

import com.dragn0007.dragnvehicles.ValiantVehiclesMain;
import com.dragn0007.dragnvehicles.vehicle.base.AbstractVehicle;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationProcessor;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class CarModel<T extends AbstractVehicle> extends DefaultedEntityGeoModel<T> {

    public CarModel() {
        super(new ResourceLocation(ValiantVehiclesMain.MODID, "car"), false);
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        return ValiantVehiclesMain.id("textures/entity/car/" + animatable.getColor().toString().toLowerCase() + ".png");
    }

    @Override
    public void setCustomAnimations(T animatable, long instanceId, AnimationState<T> state) {
        super.setCustomAnimations(animatable, instanceId, state);

        AnimationProcessor<T> processor = getAnimationProcessor();
        float partialTick = (float)state.animationTick - (int)state.animationTick;
        float rot = animatable.getWheelRot(partialTick);
        float turn = animatable.getFrontWheelRotation(partialTick);

        processor.getBone("front_wheels").setRotX(-rot);
        processor.getBone("back_wheels").setRotX(-rot);
        processor.getBone("front_wheels").setRotY(turn);
        processor.getBone("steering_wheel").setRotY(turn);
    }


}