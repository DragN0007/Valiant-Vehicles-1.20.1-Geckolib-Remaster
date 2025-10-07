package com.dragn0007.dragnvehicles.vehicle.base;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractInventoryVehicle extends AbstractVehicle implements ContainerListener {

    public SimpleContainer inventory;
    protected LazyOptional<?> itemHandler;

    public AbstractInventoryVehicle(EntityType<? extends AbstractVehicle> type, Level level, double maxSpeed, double acceleration, float turnRate,
                                    int maxHealth, double wheelWidth, double wheelLength, Vec3[] riders) {
        super(type, level, maxSpeed, acceleration, turnRate, maxHealth, wheelWidth, wheelLength, riders);
        this.createInventory();
    }

    protected void createInventory() {
        this.inventory = new SimpleContainer(0);
        this.inventory.addListener(this);
        this.itemHandler = LazyOptional.of(() -> new InvWrapper(this.inventory));
    }

    @Override
    @NotNull
    public InteractionResult interact(Player player, InteractionHand hand) {
        if(player.isShiftKeyDown()) {
            if(!this.level().isClientSide) {
                    NetworkHooks.openScreen((ServerPlayer) player, new SimpleMenuProvider((containerId, inventory, serverPlayer) ->
                            ChestMenu.threeRows(containerId, inventory, this.inventory), this.getDisplayName()));
                }
            }
        return super.interact(player, hand);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(this.isAlive() && cap == ForgeCapabilities.ITEM_HANDLER && this.itemHandler != null && this.isAlive()) {
            return itemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        if(this.itemHandler != null) {
            LazyOptional<?> oldHandler = this.itemHandler;
            this.itemHandler = null;
            oldHandler.invalidate();
        }
    }

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
    protected void onDestroyed(boolean dropItem) {
        super.onDestroyed(dropItem);
        if(!level().isClientSide)
            Containers.dropContents(level(), blockPosition(), inventory);
    }

}
