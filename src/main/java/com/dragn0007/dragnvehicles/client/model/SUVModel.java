package com.dragn0007.dragnvehicles.client.model;

import com.dragn0007.dragnvehicles.ValiantVehiclesMain;
import com.dragn0007.dragnvehicles.vehicle.SUV;
import com.dragn0007.dragnvehicles.vehicle.base.AbstractVehicle;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationProcessor;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class SUVModel<T extends AbstractVehicle> extends DefaultedEntityGeoModel<T> {

    public SUVModel() {
        super(new ResourceLocation(ValiantVehiclesMain.MODID, "suv"), false);
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        if (animatable.getVariant() == 0) {
            return ValiantVehiclesMain.id("textures/entity/suv/" + animatable.getColor().toString().toLowerCase() + ".png");
        } else {
            return (((SUV) animatable).getTextureLocation());
        }
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