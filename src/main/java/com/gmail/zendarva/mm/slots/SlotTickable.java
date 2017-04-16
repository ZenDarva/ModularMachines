package com.gmail.zendarva.mm.slots;

import com.gmail.zendarva.mm.items.Module;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotTickable extends SlotItemHandler {


    public SlotTickable(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        if (stack.getItem() instanceof Module)
            return true;
        return false;
    }



}
