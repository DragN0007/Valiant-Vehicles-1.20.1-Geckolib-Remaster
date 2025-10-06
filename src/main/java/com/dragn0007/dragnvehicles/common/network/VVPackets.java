package com.dragn0007.dragnvehicles.common.network;

import com.dragn0007.dragnvehicles.ValiantVehiclesMain;
import com.dragn0007.dragnvehicles.common.network.packets.VehicleControlPacket;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class VVPackets {

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            ValiantVehiclesMain.id("main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void register() {
        int id = 0;
        INSTANCE.registerMessage(id++, VehicleControlPacket.class, VehicleControlPacket::encode, VehicleControlPacket::decode, (p, ctx) -> p.handle(ctx.get()));
    }

}
