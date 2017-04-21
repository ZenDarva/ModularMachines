package com.gmail.zendarva.mm.items.modules;

import com.gmail.zendarva.mm.IOType;
import com.gmail.zendarva.mm.entities.MachineFrameEntity;
import com.gmail.zendarva.mm.recipie.CrusherRecipe;
import com.gmail.zendarva.mm.recipie.CrusherRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by James on 4/16/2017.
 */
public class ModuleCrusher extends BaseModule {

    @Override
    public IOType[] requires() {
        return new IOType[]{IOType.ITEMSTACK};
    }
    public IOType[] provides() {
        return new IOType[]{IOType.ITEMSTACK};
    }

    public ModuleCrusher(){
        this.unlocalizedName="crushermodule";
        this.rfPerTick=40;
    }
    @Override
    public boolean tick(MachineFrameEntity entity, ItemStack module) {
        ItemStack target = getTarget(module);
        if (target.isEmpty()) {
            if (!validateInput(entity))
                return false;
            target = (ItemStack) entity.executionStack.peek();

            if (CrusherRecipes.instance().getCrusherResult(target)== null)
                return false;
            entity.executionStack.pop();
        }

        progress++;
        if (progress >= 100)
        {
            CrusherRecipe recipie = CrusherRecipes.instance().getCrusherResult(target);

            for(ItemStack stack :recipie.output)
            {
                entity.executionStack.push(stack.copy());
            }
        }
        this.saveNBT(module, target);
        return true;
    }

    @Override
    public boolean isDone(ItemStack module) {
        return (progress >= 100);
    }

    @Override
    public void reset(ItemStack module) {
        progress = 0;
        saveNBT(module,ItemStack.EMPTY);
    }
    private void saveNBT(ItemStack stack, ItemStack target) {
        stack.getTagCompound().setTag("target", target.serializeNBT());
        this.saveNBT(stack);
    }


    private ItemStack getTarget(ItemStack module) {
        NBTTagCompound tag = module.getTagCompound().getCompoundTag("target");
        if (tag.hasNoTags())
            return ItemStack.EMPTY;
        return new ItemStack(tag);
    }
}

