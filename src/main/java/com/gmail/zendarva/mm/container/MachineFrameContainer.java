package com.gmail.zendarva.mm.container;


import com.gmail.zendarva.mm.entities.MachineFrameEntity;
import com.gmail.zendarva.mm.slots.SlotTickable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class MachineFrameContainer extends Container {

    private MachineFrameEntity entity;
    int startX = 31;
    int startY = 145;

    public MachineFrameContainer(IInventory playerInventory, MachineFrameEntity entity){
        this.entity=entity;
        addOwnSlots();
        addPlayerSlots(playerInventory);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        return ItemStack.EMPTY;
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
        IItemHandler handler = entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,null);
        addSlotToContainer(new SlotItemHandler(handler,0,11,61 ));
        addSlotToContainer(new SlotItemHandler(handler,1,191,61 ));
        IItemHandler grid = entity.internalGrid;
        int index = 0;
        for (int x = 0; x< 5; x++){
            for (int y=0;y<5;y++){
                addSlotToContainer(new SlotTickable(grid,index,x*23 + 61, y*23 + 24));
                index++;
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}
