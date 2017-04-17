package com.gmail.zendarva.mm.items.modules;

import com.gmail.zendarva.mm.IOType;
import com.gmail.zendarva.mm.entities.MachineFrameEntity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

/**
 * Created by James on 4/16/2017.
 */
public class ModuleKiller extends BaseModule {
    public ModuleKiller() {
        this.unlocalizedName ="killermodule";
    }

    @Override
    public IOType[] requires() {
        return new IOType[]{IOType.ENTITYLIVING};
    }

    @Override
    public boolean tick(MachineFrameEntity entity, ItemStack module) {
        if (!validateInput(entity))
            return false;
        EntityLiving living = (EntityLiving) entity.executionStack.pop();
        living.attackEntityFrom(DamageSource.GENERIC,living.getHealth());
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
