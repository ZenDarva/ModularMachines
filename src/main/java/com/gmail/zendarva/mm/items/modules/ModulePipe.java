package com.gmail.zendarva.mm.items.modules;

import com.gmail.zendarva.mm.IOType;
import com.gmail.zendarva.mm.entities.MachineFrameEntity;
import net.minecraft.item.ItemStack;

/**
 * Created by James on 4/16/2017.
 */
public class ModulePipe extends BaseModule {

    public ModulePipe(){
        this.unlocalizedName="pipemodule";
    }

    @Override
    public IOType[] requires() {
        return new IOType[]{IOType.ANY};
    }

    @Override
    public boolean tick(MachineFrameEntity entity, ItemStack module) {
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
