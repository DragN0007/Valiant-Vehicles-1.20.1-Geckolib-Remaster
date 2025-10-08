package com.dragn0007.dragnvehicles.compat.jade;

import com.dragn0007.dragnvehicles.vehicle.base.AbstractVehicle;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class JadePlugin implements IWailaPlugin {

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerEntityComponent(new VehicleTooltip(), AbstractVehicle.class);
    }
}
