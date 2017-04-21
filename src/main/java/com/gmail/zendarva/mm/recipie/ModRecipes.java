package com.gmail.zendarva.mm.recipie;

import com.gmail.zendarva.mm.ModItems;
import com.gmail.zendarva.mm.items.Module;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by James on 4/16/2017.
 */
public class ModRecipes {
    public static void init(){
        CrusherRecipes.instance().registerRecipie(new CrusherRecipe(new ItemStack(Blocks.IRON_ORE), new ItemStack[]{new ItemStack(ModItems.dust,1,1),new ItemStack(ModItems.dust,1,1)}));
        CrusherRecipes.instance().registerRecipie(new CrusherRecipe(new ItemStack(Blocks.GOLD_ORE), new ItemStack[]{new ItemStack(ModItems.dust,1,2),new ItemStack(ModItems.dust,1,2)}));

        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(ModItems.dust,1,1),new ItemStack(Items.IRON_INGOT,1),5);
        FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(ModItems.dust,1,2),new ItemStack(Items.GOLD_INGOT,1),5);

        mmRecipes();
    }

    static void mmRecipes()
    {
        ItemStack frame = new ItemStack(ModItems.module,1,17);
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.module,1,17), "A A"," B ","A A", 'A',Items.CLAY_BALL,'B', Items.IRON_INGOT);
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.module,1,1), " A ", " B ", 'A', Blocks.HOPPER, 'B', frame);
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.module,1,2), "   ", " B ", " A ", 'A', Blocks.HOPPER, 'B', frame);
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.module,1,3), " A ", " B ", 'A', Blocks.FURNACE, 'B', frame);
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.module, 1, 4), "BAB", " C ", 'A', Blocks.PISTON, 'B',Items.FLINT, 'C',frame);
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.module, 1, 5), " A ", " B ", " A ", 'A', Items.REDSTONE, 'B', frame);
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.module,1,6), " A ", " B ", 'A', Items.LEAD, 'B',frame );
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.module, 1, 7), " A ", " B ", 'A', Items.DIAMOND_PICKAXE, 'B', frame);
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.module, 1, 8), " A ", " B ", 'A', Items.COMPARATOR, 'B', frame);
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.module, 1, 9)," A ", " B ", " C ", 'A', Items.LEAD, 'B',frame,'C',Items.ROTTEN_FLESH);
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.module, 1, 10), " A ", " B ", 'A', Items.DIAMOND_SWORD, 'B', frame);
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.module, 1, 11), " A ", " B ", " C ", 'A', Items.LEAD, 'B',frame,'C',Items.REDSTONE);
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.module, 1, 12)," A ", " B ", 'A', Items.ENDER_EYE, 'B',frame);
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.module, 1, 13)," A ", " B ", 'A', Items.ENDER_PEARL, 'B', frame);
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.module, 1, 14)," A ", " B ", " C ", 'A', Items.LEAD, 'B',frame,'C',Items.EGG);
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.module, 1, 15)," A ", " B ", " C ", 'A', Blocks.REDSTONE_BLOCK, 'B',frame,'C',Items.REDSTONE);
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.module, 1, 16)," A ", " B ", " C ", 'A', Blocks.REDSTONE_BLOCK, 'B',frame,'C',Items.ENDER_PEARL);
        GameRegistry.addShapedRecipe(new ItemStack(ModItems.machineFrameBlock), "AAA","ABA","AAA", 'A', Items.IRON_INGOT, 'B', frame);

    }
}
