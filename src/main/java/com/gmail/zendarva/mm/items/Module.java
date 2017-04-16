package com.gmail.zendarva.mm.items;

import com.gmail.zendarva.mm.items.modules.ModuleManager;
import net.minecraft.client.renderer.EnumFaceDirection;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Module extends Item {



    public Module()
    {
        super();
        this.setHasSubtypes(true);
        this.setUnlocalizedName("basemodule");
        setRegistryName("basemodule");
        GameRegistry.register(this);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return ModuleManager.getInstance().getModules().get(stack.getItemDamage()).name;
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
        ModuleManager.getInstance().getModules().forEach(f->{
            if (f.clazz != null)
                subItems.add(new ItemStack(itemIn, 1,f.id));
        });

    }



}
