package com.dragn0007.dragnvehicles.menus;

import com.dragn0007.dragnvehicles.vehicle.Trailer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public abstract class AbstractTrailerMenu extends AbstractContainerMenu {

    private final Trailer trailer;

    public AbstractTrailerMenu(MenuType<? extends AbstractTrailerMenu> type, int id, Inventory inventory, Trailer trailer,
                               int perRow, int xInv, int yInv) {
        super(type, id);
        this.trailer = trailer;

        for(int y = 0; y < trailer.getItems().getSlots() / perRow; y++) {
            for(int x = 0; x < perRow; x++) {
                addSlot(new SlotItemHandler(trailer.getItems(), x + y*perRow, 8 + x*18, 18 + y*18));
            }
        }

        for(int x = 0; x < 9; x++) {
            addSlot(new Slot(inventory, x, xInv + x*18, yInv + 58));
        }

        for(int y = 0; y < 3; y++) {
            for(int x = 0; x < 9; x++) {
                addSlot(new Slot(inventory, 9 + x + y*9, xInv + x*18, yInv + y*18));
            }
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack copy = ItemStack.EMPTY;
        Slot slot = slots.get(index);

        if(slot.hasItem()) {
            ItemStack item = slot.getItem();
            copy = item.copy();

            int size = trailer.getItems().getSlots();
            if(index < size) { // If item is in wagon, move to player
                if(!moveItemStackTo(item, size, slots.size(), false))
                    return ItemStack.EMPTY;
            } else { // If not, move to wagon
                if(!moveItemStackTo(item, 0, size, false))
                    return ItemStack.EMPTY;
            }

            if(item.isEmpty())
                slot.setByPlayer(ItemStack.EMPTY);
            else
                slot.setChanged();
        }

        return copy;
    }

    @Override
    public boolean stillValid(Player player) {
        return trailer.isAlive() && trailer.distanceTo(player) < 8.0F;
    }

    public static Trailer getTrailer(Inventory inventory, FriendlyByteBuf data) {
        return inventory.player.level().getEntity(data.readInt()) instanceof Trailer trailer ? trailer : null;
    }

}
