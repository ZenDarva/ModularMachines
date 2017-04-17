package com.gmail.zendarva.mm.recipie;

import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by James on 4/16/2017.
 */
public class CrusherRecipies {
    private LinkedList<CrusherRecipie> recipies;
    private CrusherRecipies(){
        recipies= new LinkedList<>();
    };
    private static CrusherRecipies myInstance;

    public static CrusherRecipies instance(){
        if (myInstance == null)
            myInstance= new CrusherRecipies();
        return myInstance;
    }

    public void registerRecipie(CrusherRecipie recipie) {
        recipies.add(recipie);
    }

    public CrusherRecipie getCrusherResult(ItemStack stack){
        for (CrusherRecipie recipie: recipies) {
            if (ItemStack.areItemStacksEqual(stack, recipie.input))
                return recipie;
        }
        return null;
    }
}
