package com.dragn0007.dragnvehicles.menus;

import com.dragn0007.dragnvehicles.vehicle.Trailer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;

public class LivestockTrailerMenu extends AbstractTrailerMenu {

    public LivestockTrailerMenu(int id, Inventory inventory, Trailer trailer) {
        super(MenuRegistry.LIVESTOCK_TRAILER.get(), id, inventory, trailer, 9, 8, 50);
    }

    public LivestockTrailerMenu(int id, Inventory inventory, FriendlyByteBuf data) {
        this(id, inventory, getTrailer(inventory, data));
    }

}
