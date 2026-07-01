package com.dragn0007.dragnvehicles.util;

import net.minecraftforge.common.ForgeConfigSpec;

public class ValiantVehiclesCommonConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> FUEL_BURN_TIME;
    public static final ForgeConfigSpec.ConfigValue<Double> CAR_SPEED_MULT;
    public static final ForgeConfigSpec.ConfigValue<Double> CAR_SPEED_ACC;
    public static final ForgeConfigSpec.ConfigValue<Float> CAR_SPEED_TURN;
    public static final ForgeConfigSpec.ConfigValue<Integer> CAR_HEALTH;
    public static final ForgeConfigSpec.ConfigValue<Double> PICKUP_SPEED_MULT;
    public static final ForgeConfigSpec.ConfigValue<Double> PICKUP_SPEED_ACC;
    public static final ForgeConfigSpec.ConfigValue<Float> PICKUP_SPEED_TURN;
    public static final ForgeConfigSpec.ConfigValue<Integer> PICKUP_HEALTH;
    public static final ForgeConfigSpec.ConfigValue<Double> SUV_SPEED_MULT;
    public static final ForgeConfigSpec.ConfigValue<Double> SUV_SPEED_ACC;
    public static final ForgeConfigSpec.ConfigValue<Float> SUV_SPEED_TURN;
    public static final ForgeConfigSpec.ConfigValue<Integer> SUV_HEALTH;
    public static final ForgeConfigSpec.ConfigValue<Double> CLASSIC_SPEED_MULT;
    public static final ForgeConfigSpec.ConfigValue<Double> CLASSIC_SPEED_ACC;
    public static final ForgeConfigSpec.ConfigValue<Float> CLASSIC_SPEED_TURN;
    public static final ForgeConfigSpec.ConfigValue<Integer> CLASSIC_HEALTH;
    public static final ForgeConfigSpec.ConfigValue<Double> SPORTCAR_SPEED_MULT;
    public static final ForgeConfigSpec.ConfigValue<Double> SPORTCAR_SPEED_ACC;
    public static final ForgeConfigSpec.ConfigValue<Float> SPORTCAR_SPEED_TURN;
    public static final ForgeConfigSpec.ConfigValue<Integer> SPORTCAR_HEALTH;
    public static final ForgeConfigSpec.ConfigValue<Double> MOTORCYCLE_SPEED_MULT;
    public static final ForgeConfigSpec.ConfigValue<Double> MOTORCYCLE_SPEED_ACC;
    public static final ForgeConfigSpec.ConfigValue<Float> MOTORCYCLE_SPEED_TURN;
    public static final ForgeConfigSpec.ConfigValue<Integer> MOTORCYCLE_HEALTH;

    static {
        BUILDER.push("Fuel");
        FUEL_BURN_TIME = BUILDER.define("Fuel Burn Time Ticks", 6000);
        BUILDER.pop();

        BUILDER.push("Car");
        CAR_SPEED_MULT = BUILDER.define("Modern Car Speed Multiplier", 0.7D);
        CAR_SPEED_ACC = BUILDER.define("Modern Car Acceleration", 2.0D);
        CAR_SPEED_TURN = BUILDER.define("Modern Car Turn Rate", 4.0F);
        CAR_HEALTH = BUILDER.define("Modern Car Max Health", 120);
        BUILDER.pop();

        BUILDER.push("Pickup");
        PICKUP_SPEED_MULT = BUILDER.define("Pickup Truck Speed Multiplier", 0.6D);
        PICKUP_SPEED_ACC = BUILDER.define("Pickup Truck Acceleration", 1.8D);
        PICKUP_SPEED_TURN = BUILDER.define("Pickup Truck Turn Rate", 3.7F);
        PICKUP_HEALTH = BUILDER.define("Pickup Truck Max Health", 160);
        BUILDER.pop();

        BUILDER.push("SUV");
        SUV_SPEED_MULT = BUILDER.define("SUV Speed Multiplier", 0.65D);
        SUV_SPEED_ACC = BUILDER.define("SUV Acceleration", 1.6D);
        SUV_SPEED_TURN = BUILDER.define("SUV Turn Rate", 3.6F);
        SUV_HEALTH = BUILDER.define("SUV Max Health", 160);
        BUILDER.pop();

        BUILDER.push("Classic");
        CLASSIC_SPEED_MULT = BUILDER.define("Classic Car Speed Multiplier", 0.7D);
        CLASSIC_SPEED_ACC = BUILDER.define("Classic Car Acceleration", 1.8D);
        CLASSIC_SPEED_TURN = BUILDER.define("Classic Car Turn Rate", 4.0F);
        CLASSIC_HEALTH = BUILDER.define("Classic Car Max Health", 100);
        BUILDER.pop();

        BUILDER.push("Sportcar");
        SPORTCAR_SPEED_MULT = BUILDER.define("Sportcar Speed Multiplier", 0.8D);
        SPORTCAR_SPEED_ACC = BUILDER.define("Sportcar Acceleration", 2.4D);
        SPORTCAR_SPEED_TURN = BUILDER.define("Sportcar Turn Rate", 4.0F);
        SPORTCAR_HEALTH = BUILDER.define("Sportcar Max Health", 80);
        BUILDER.pop();

        BUILDER.push("Motorcycle");
        MOTORCYCLE_SPEED_MULT = BUILDER.define("Motorcycle Speed Multiplier", 0.8D);
        MOTORCYCLE_SPEED_ACC = BUILDER.define("Motorcycle Acceleration", 2.6D);
        MOTORCYCLE_SPEED_TURN = BUILDER.define("Motorcycle Turn Rate", 4.0F);
        MOTORCYCLE_HEALTH = BUILDER.define("Motorcycle Max Health", 60);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
