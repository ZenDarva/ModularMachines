package com.gmail.zendarva.mm.items.modules;

import com.gmail.zendarva.mm.entities.MachineFrameEntity;
import net.minecraft.item.ItemStack;

/**
 * Created by James on 4/20/2017.
 */
public class ModuleNop extends BaseModule {
    public ModuleNop() {this.unlocalizedName="moduleframe";
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
