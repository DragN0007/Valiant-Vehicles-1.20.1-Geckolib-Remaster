package com.dragn0007.dragnvehicles.vehicle;

import com.dragn0007.dragnvehicles.ValiantVehiclesMain;
import com.dragn0007.dragnvehicles.item.VVItems;
import com.dragn0007.dragnvehicles.util.ValiantVehiclesCommonConfig;
import com.dragn0007.dragnvehicles.vehicle.base.AbstractInventoryVehicle;
import com.dragn0007.dragnvehicles.vehicle.base.AbstractVehicle;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public class Pickup extends AbstractInventoryVehicle implements ContainerListener {

    public static final Vec3[] RIDERS = new Vec3[] {
            new Vec3(0.5D, 0.65D, 0.2D),
            new Vec3(-0.5D, 0.65D, 0.2D)
    };

    public Pickup(EntityType<? extends AbstractVehicle> type, Level level) {
        super(type, level,
                ValiantVehiclesCommonConfig.PICKUP_SPEED_MULT.get(),
                ValiantVehiclesCommonConfig.PICKUP_SPEED_ACC.get(),
                ValiantVehiclesCommonConfig.PICKUP_SPEED_TURN.get(),
                ValiantVehiclesCommonConfig.PICKUP_HEALTH.get(),
                1.25D,
                1.25D,
                RIDERS);
        this.createInventory();
    }

    public ItemStack getPickResult() {
        return VVItems.TRUCK_SPAWN_EGG.get().getDefaultInstance();
    }

    @Override
    protected void createInventory() {
        this.inventory = new SimpleContainer(54);
        this.inventory.addListener(this);
        this.itemHandler = LazyOptional.of(() -> new InvWrapper(this.inventory));
    }

    @Override
    @NotNull
    public InteractionResult interact(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        Item item = stack.getItem();

        if (player.isShiftKeyDown()) {
            if (item instanceof DyeItem) {
                DyeItem dyeitem = (DyeItem) item;
                DyeColor dyecolor = dyeitem.getDyeColor();
                if ((dyecolor != this.getColor()) || this.getVariant() > 0) {
                    this.setColor(dyecolor);
                    this.setVariant(0);
                    if (!player.getAbilities().instabuild) {
                        stack.shrink(1);
                    }
                    return InteractionResult.SUCCESS;
                }
            } else if (stack.is(Items.BONE)) {
                this.setVariant(1);
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
        } else if (stack.isEmpty() && player.isShiftKeyDown()) {
//            if ((item != VVItems.CAR_KEY.get())) {
//            if ((this.isLocked() && this.getOwner().equals(player.getUUID())) || (!this.isLocked())) {
            if (!this.level().isClientSide) {
                NetworkHooks.openScreen((ServerPlayer) player, new SimpleMenuProvider((containerId, inventory, serverPlayer) ->
                        ChestMenu.sixRows(containerId, inventory, this.inventory), this.getDisplayName()));
            }
//            }
//            }
        }

        return super.interact(player, hand);
    }

    public enum PaintColor {
        NONE(new ResourceLocation(ValiantVehiclesMain.MODID, "textures/entity/none.png")),
        GILCASTER(new ResourceLocation(ValiantVehiclesMain.MODID, "textures/entity/pickup/gilcaster.png"));

        public final ResourceLocation resourceLocation;
        PaintColor(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static PaintColor variantFromOrdinal(int variant) { return PaintColor.values()[variant % PaintColor.values().length];
        }
    }

    public static final EntityDataAccessor<Integer> DATA_PAINT_COLOR = SynchedEntityData.defineId(Pickup.class, EntityDataSerializers.INT);
    public ResourceLocation getTextureLocation() {
        return PaintColor.variantFromOrdinal(getVariant()).resourceLocation;
    }
    public int getVariant() {
        return this.entityData.get(DATA_PAINT_COLOR);
    }
    public void setVariant(int variant) {
        this.entityData.set(DATA_PAINT_COLOR, variant);
    }

    @Override
    public void containerChanged(Container container) {}

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.createInventory();
        ListTag listTag = compoundTag.getList("Items", Tag.TAG_COMPOUND);
        for(int i = 0; i < listTag.size(); i++) {
            CompoundTag tag = listTag.getCompound(i);
            int j = tag.getByte("Slot") & 255;
            if(j < this.inventory.getContainerSize()) {
                this.inventory.setItem(j, ItemStack.of(tag));
            }
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        ListTag listTag = new ListTag();
        for(int i = 0; i < this.inventory.getContainerSize(); i++) {
            ItemStack itemStack = this.inventory.getItem(i);
            if(!itemStack.isEmpty()) {
                CompoundTag tag = new CompoundTag();
                tag.putByte("Slot", (byte)i);
                itemStack.save(tag);
                listTag.add(tag);
            }
        }
        compoundTag.put("Items", listTag);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_PAINT_COLOR, 0);
    }
}
