package com.gmail.zendarva.mm.items.modules;

import com.gmail.zendarva.mm.IOType;
import com.gmail.zendarva.mm.entities.MachineFrameEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by James on 4/18/2017.
 */
public class ModuleGenerator extends BaseModule {

    public ModuleGenerator() {
        this.unlocalizedName="generatormodule";
    }

    @Override
    public IOType[] requires() {
        return new IOType[]{IOType.ITEMSTACK};
    }

    @Override
    public boolean tick(MachineFrameEntity entity, ItemStack module) {
        int maxProgress = 0;
        ItemStack target =getTarget(module);
        if (target.isEmpty()) {
            if (!validateInput(entity))
                return false;
            target = (ItemStack) entity.executionStack.peek();
            if ((maxProgress = TileEntityFurnace.getItemBurnTime(target)) == 0)
                return false;
            entity.executionStack.pop();
        }
        else
        {
            maxProgress = module.getTagCompound().getInteger("maxProgress");
        }
        progress+=1;
        entity.energyStorage.receiveEnergy(80,false);
        entity.markDirty();
        saveNBT(module,target,maxProgress);
        return true;
    }

    @Override
    public boolean isDone(ItemStack module) {
        int maxProgress = module.getTagCompound().getInteger("maxProgress");
        return progress > maxProgress;
    }
    private void saveNBT(ItemStack stack, ItemStack target, int maxProgress) {
        stack.getTagCompound().setTag("target", target.serializeNBT());
        stack.getTagCompound().setInteger("maxProgress",maxProgress);
        this.saveNBT(stack);
    }

    @Override
    public void reset(ItemStack module) {
        this.saveNBT(module,ItemStack.EMPTY,0);
    }

    private ItemStack getTarget(ItemStack module) {
        NBTTagCompound tag = module.getTagCompound().getCompoundTag("target");
        if (tag.hasNoTags())
            return ItemStack.EMPTY;
        return new ItemStack(tag);
    }
}
