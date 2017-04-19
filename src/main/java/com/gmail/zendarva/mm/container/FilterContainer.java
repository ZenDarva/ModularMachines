package com.gmail.zendarva.mm.container;

import com.gmail.zendarva.mm.slots.SlotTickable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

/**
 * Created by James on 4/17/2017.
 */
public class FilterContainer extends Container {
    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    public ItemStackHandler handler;
    ItemStack source;

    int startX = 9;
    int startY = 84;

    public FilterContainer(IInventory playerInventory, ItemStack source){
        handler = new ItemStackHandler(9);
        if (source.getTagCompound() == null)
        {
            source.setTagCompound(new NBTTagCompound());
        }
        handler.deserializeNBT(source.getTagCompound().getCompoundTag("inventory"));
        this.source = source;
        addOwnSlots();
        addPlayerSlots(playerInventory);
    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn) {
        source.getTagCompound().setTag("inventory",handler.serializeNBT());
    }
    private void addPlayerSlots(IInventory playerInventory) {
        // Slots for the main inventory
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                int x = startX + col * 18;
                int y = row * 18 + startY;
                this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 9, x, y));
            }
        }

        // Slots for the hotbar
        for (int row = 0; row < 9; ++row) {
            int x = startX + row * 18;
            int y = 58 + startY;
            this.addSlotToContainer(new Slot(playerInventory, row, x, y));
        }




    }

    private void addOwnSlots(){
//        IItemHandler handler = entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,null);
//        addSlotToContainer(new SlotItemHandler(handler,0,11,61 ));
//        addSlotToContainer(new SlotItemHandler(handler,1,191,61 ));
//        IItemHandler grid = entity.internalGrid;
//        int index = 0;
//        for (int x = 0; x< 5; x++){
//            for (int y=0;y<5;y++){
//                addSlotToContainer(new SlotTickable(grid,index,x*23 + 61, y*23 + 24));
//                index++;
//            }
//        }
    }
}
