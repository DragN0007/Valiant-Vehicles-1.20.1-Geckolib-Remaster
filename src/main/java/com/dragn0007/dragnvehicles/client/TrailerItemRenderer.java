package com.dragn0007.dragnvehicles.client;

import com.dragn0007.dragnvehicles.vehicle.Trailer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class TrailerItemRenderer extends BlockEntityWithoutLevelRenderer {

    private final EntityType<? extends Trailer> type;
    private Trailer entity = null;

    public TrailerItemRenderer(EntityType<? extends Trailer> type) {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
        this.type = type;
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext context, PoseStack pose, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        if(entity == null)
            entity = type.create(Minecraft.getInstance().level);
        Minecraft.getInstance().getEntityRenderDispatcher().render(entity, 0, 0, 0, 0, 0, pose, buffer, packedLight);
    }

}
