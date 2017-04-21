package com.gmail.zendarva.mm.recipie;

import net.minecraft.item.ItemStack;

import java.util.LinkedList;

/**
 * Created by James on 4/16/2017.
 */
public class CrusherRecipes {
    private LinkedList<CrusherRecipe> recipies;
    private CrusherRecipes(){
        recipies= new LinkedList<>();
    };
    private static CrusherRecipes myInstance;

    public static CrusherRecipes instance(){
        if (myInstance == null)
            myInstance= new CrusherRecipes();
        return myInstance;
    }

    public void registerRecipie(CrusherRecipe recipie) {
        recipies.add(recipie);
    }

    public CrusherRecipe getCrusherResult(ItemStack stack){
        for (CrusherRecipe recipie: recipies) {
            if (ItemStack.areItemStacksEqual(stack, recipie.input))
                return recipie;
        }
        return null;
    }
}
