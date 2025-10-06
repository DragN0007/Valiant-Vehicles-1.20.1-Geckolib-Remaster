package com.dragn0007.dragnvehicles.util;

import net.minecraftforge.common.ForgeConfigSpec;

public class ValiantVehiclesCommonConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Double> CAR_SPEED_MULT;
    public static final ForgeConfigSpec.ConfigValue<Double> CAR_SPEED_ACC;
    public static final ForgeConfigSpec.ConfigValue<Float> CAR_SPEED_TURN;
    public static final ForgeConfigSpec.ConfigValue<Integer> CAR_HEALTH;

    static {

        BUILDER.push("Speed");
        CAR_SPEED_MULT = BUILDER.define("Modern Car Speed Multiplier", 0.7D);
        BUILDER.pop();

        BUILDER.push("Acceleration");
        CAR_SPEED_ACC = BUILDER.define("Modern Car Acceleration", 2.0D);
        BUILDER.pop();

        BUILDER.push("Turning");
        CAR_SPEED_TURN = BUILDER.define("Modern Car Turn Rate", 4.0F);
        BUILDER.pop();

        BUILDER.push("Health");
        CAR_HEALTH = BUILDER.define("Modern Car Max Health", 120);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
