package com.gmail.zendarva.mm.items.modules;

import com.gmail.zendarva.mm.IOType;
import com.gmail.zendarva.mm.entities.MachineFrameEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by James on 4/16/2017.
 */
public class FurnaceModule extends BaseModule {

    @Override
    public IOType requires() {
        return IOType.ItemStack;
    }

    public FurnaceModule(){
        this.unlocalizedName="furnacemodule";

    }
    @Override
    public boolean tick(MachineFrameEntity entity, ItemStack module) {
        this.loadNBT(module);
        ItemStack target =getTarget(module);
         if (target.isEmpty()) {
             if (!validateInput(entity))
                 return false;
             target = (ItemStack) entity.executionStack.peek();

             if (FurnaceRecipes.instance().getSmeltingResult(target).isEmpty())
                 return false;
             entity.executionStack.pop();
         }


         progress+=2;
         if (progress >= 100)
         {
             entity.executionStack.push(FurnaceRecipes.instance().getSmeltingResult(target).copy());
         }
        this.saveNBT(module, target);
         return true;
    }

    private void saveNBT(ItemStack stack, ItemStack target) {
        stack.getTagCompound().setTag("target", target.serializeNBT());
        this.saveNBT(stack);
    }

    @Override
    public boolean isDone(ItemStack module) {
        return progress >= 100;
    }

    @Override
    public void reset(ItemStack module) {
        progress = 0;
        saveNBT(module,ItemStack.EMPTY);
    }
    private ItemStack getTarget(ItemStack module) {
        NBTTagCompound tag = module.getTagCompound().getCompoundTag("target");
        if (tag.hasNoTags())
            return ItemStack.EMPTY;
        return new ItemStack(tag);
    }
}
