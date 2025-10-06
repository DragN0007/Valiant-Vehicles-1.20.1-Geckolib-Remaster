package com.dragn0007.dragnvehicles.client;

import com.dragn0007.dragnvehicles.vehicle.base.AbstractVehicle;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class VehicleItemRenderer extends BlockEntityWithoutLevelRenderer {

    private final EntityType<? extends AbstractVehicle> type;
    private AbstractVehicle entity = null;

    public VehicleItemRenderer(EntityType<? extends AbstractVehicle> type) {
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
