package com.gmail.zendarva.mm.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by James on 4/16/2017.
 */
public class ItemDust extends Item {

    public ItemDust()
    {
        super();
        this.setHasSubtypes(true);
        this.setUnlocalizedName("itemdust");
        setRegistryName("itemdust");
        GameRegistry.register(this);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        switch (stack.getItemDamage()){
            case 1:
                return "irondust";
            case 2:
                return "golddust";
        }
            return "iamerror";
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
        subItems.add(new ItemStack(this,1,1));
        subItems.add(new ItemStack(this,1,2));
    }
}
