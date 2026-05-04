package com.dragn0007.dragnvehicles.vehicle;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public interface TruckHitchable {

    Vec3 getHitchPoint();

    /**
     * Matches {@link Entity#getYRot()}
     */
    float getYRot();

    /**
     * Matches {@link Entity#position()}
     */
    Vec3 position();

    /**
     * Matches {@link Entity#getXRot()}
     */
    float getXRot();

}
