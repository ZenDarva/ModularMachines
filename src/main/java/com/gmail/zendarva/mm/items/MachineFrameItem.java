package com.gmail.zendarva.mm.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class MachineFrameItem extends ItemBlock {
    public MachineFrameItem(Block block) {
        super(block);
        setHasSubtypes(true);
        this.setMaxDamage(0);
    }



    @Override
    public int getMetadata(int damage) {
        return damage;
    }
}