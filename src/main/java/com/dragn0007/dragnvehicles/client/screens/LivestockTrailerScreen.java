package com.dragn0007.dragnvehicles.client.screens;

import com.dragn0007.dragnvehicles.ValiantVehiclesMain;
import com.dragn0007.dragnvehicles.menus.LivestockTrailerMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class LivestockTrailerScreen extends AbstractTrailerScreen<LivestockTrailerMenu> {

    public LivestockTrailerScreen(LivestockTrailerMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, ValiantVehiclesMain.id("textures/gui/livestock_trailer.png"), 256, 256);
        this.imageWidth = 176;
        this.imageHeight = 132;
        this.inventoryLabelX = 8;
        this.inventoryLabelY = 38;
    }

}
