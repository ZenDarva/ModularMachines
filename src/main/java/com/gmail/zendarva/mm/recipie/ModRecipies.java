package com.gmail.zendarva.mm.recipie;

import com.gmail.zendarva.mm.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

/**
 * Created by James on 4/16/2017.
 */
public class ModRecipies {
    public static void init(){
        CrusherRecipies.instance().registerRecipie(new CrusherRecipie(new ItemStack(Blocks.IRON_ORE), new ItemStack[]{new ItemStack(ModItems.dust,1,1),new ItemStack(ModItems.dust,1,1)}));
        CrusherRecipies.instance().registerRecipie(new CrusherRecipie(new ItemStack(Blocks.GOLD_ORE), new ItemStack[]{new ItemStack(ModItems.dust,1,2),new ItemStack(ModItems.dust,1,2)}));

        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(ModItems.dust,1,1),new ItemStack(Items.IRON_INGOT,1),5);
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(ModItems.dust,1,2),new ItemStack(Items.GOLD_INGOT,1),5);

    }
}
