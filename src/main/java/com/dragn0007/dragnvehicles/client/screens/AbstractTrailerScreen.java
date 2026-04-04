package com.dragn0007.dragnvehicles.client.screens;

import com.dragn0007.dragnvehicles.menus.AbstractTrailerMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public abstract class AbstractTrailerScreen<T extends AbstractTrailerMenu> extends AbstractContainerScreen<T> {

    private final ResourceLocation texture;
    private final int texWidth;
    private final int texHeight;

    public AbstractTrailerScreen(T menu, Inventory inventory, Component title, ResourceLocation texture, int texWidth, int texHeight) {
        super(menu, inventory, title);
        this.texture = texture;
        this.texWidth = texWidth;
        this.texHeight = texHeight;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        graphics.blit(texture, leftPos, topPos, 0, 0, imageWidth, imageHeight, texWidth, texHeight);
    }

}
