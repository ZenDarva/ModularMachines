package com.gmail.zendarva.mm.items.modules;

import com.gmail.zendarva.mm.IOType;
import com.gmail.zendarva.mm.entities.MachineFrameEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

/**
 * Created by James on 4/16/2017.
 */
public class OutputModule extends BaseModule {
    @Override
    public IOType requires() {
        return IOType.ItemStack;
    }

    public OutputModule() {
        this.unlocalizedName="outputmodule";
    }

    @Override
    public boolean tick(MachineFrameEntity entity, ItemStack module) {
        if (!validateInput(entity))
            return false;
        IItemHandler handler = entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,null);
        ItemStack stack = (ItemStack) entity.executionStack.peek();
        handler.insertItem(1,stack,false);


        entity.executionStack.pop();
        return true;

    }

    @Override
    public boolean isDone(ItemStack Module) {
        return true;
    }

    @Override
    public void reset(ItemStack module) {

    }
}
