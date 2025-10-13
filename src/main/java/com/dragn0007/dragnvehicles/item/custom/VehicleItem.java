package com.dragn0007.dragnvehicles.item.custom;

import com.dragn0007.dragnvehicles.client.VehicleItemRenderer;
import com.dragn0007.dragnvehicles.vehicle.base.AbstractVehicle;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class VehicleItem extends Item {

    private final Supplier<EntityType<? extends AbstractVehicle>> entityType;

    public VehicleItem(Supplier<EntityType<? extends AbstractVehicle>> entityType, Properties properties) {
        super(properties);
        this.entityType = entityType;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();

        if(context.getClickedFace() != Direction.UP)
            return InteractionResult.PASS;

        if(level.isClientSide)
            return InteractionResult.SUCCESS;


        AbstractVehicle vehicle = entityType.get().create(level);
        vehicle.setPos(Vec3.atBottomCenterOf(context.getClickedPos().above()).add(0, 0.05D, 0));
        vehicle.setYRot(context.getPlayer().getYHeadRot());

        CompoundTag nbt = context.getItemInHand().getTag();
        if(nbt != null && nbt.contains("color"))
            nbt.putByte("color", (byte)vehicle.getColor().getId());

        vehicle.setOwner(context.getPlayer().getUUID());
        vehicle.setColor(DyeColor.WHITE);
        level.addFreshEntity(vehicle);

        context.getItemInHand().shrink(1);
        return InteractionResult.CONSUME;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {

            private VehicleItemRenderer renderer = null;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if(renderer == null)
                    renderer = new VehicleItemRenderer(entityType.get());
                return renderer;
            }

        });
    }

}
