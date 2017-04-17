package com.gmail.zendarva.mm.recipie;

import net.minecraft.item.ItemStack;

/**
 * Created by James on 4/16/2017.
 */
public class CrusherRecipie{
    public ItemStack input;
    public ItemStack[] output;

    public CrusherRecipie(ItemStack input, ItemStack[] output){
        this.input=input;
        this.output=output;
    }
}