package com.dragn0007.dragnvehicles.util;

import net.minecraftforge.common.ForgeConfigSpec;

public class ValiantVehiclesClientConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> HUD_X;
    public static final ForgeConfigSpec.ConfigValue<Integer> HUD_Y;

    static {
        BUILDER.push("Client");
        HUD_X = BUILDER.define("Fuel HUD X Location", 92);
        HUD_Y = BUILDER.define("Fuel HUD Y Location", 52);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
