package com.gmail.zendarva.mm.items.modules;

import com.gmail.zendarva.mm.IOType;
import com.gmail.zendarva.mm.entities.MachineFrameEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class InputModule extends BaseModule {

    public IOType[] provides() {
        return new IOType[]{IOType.ITEMSTACK};
    }
    public InputModule()
    {
        super();
        this.unlocalizedName ="inputmodule";
    }

    @Override
    public boolean tick(MachineFrameEntity entity, ItemStack module) {
        IItemHandler handler = entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,null);
        ItemStack stack = handler.getStackInSlot(0);
        if (stack == ItemStack.EMPTY)
            return false;
        ItemStack newStack =handler.extractItem(0,1,false);
        entity.executionStack.push(newStack);
        return true;
    }

    @Override
    public boolean isDone(ItemStack module) {
        return true;
    }

    @Override
    public void reset(ItemStack module) {

    }
}
