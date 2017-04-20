package com.gmail.zendarva.mm.items;

import com.gmail.zendarva.mm.MM;
import com.gmail.zendarva.mm.items.modules.BaseModule;
import com.gmail.zendarva.mm.items.modules.ModuleManager;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;

/**
 * Created by James on 4/16/2017.
 */
public class ItemDust extends Item {

    ModelResourceLocation ironLoc;
    ModelResourceLocation goldLoc;

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

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ironLoc = new ModelResourceLocation("modularmachines:irondust");
        ModelBakery.registerItemVariants(this, ironLoc);
        goldLoc=new ModelResourceLocation("modularmachines:golddust");
        ModelBakery.registerItemVariants(this, goldLoc);

        ModelLoader.setCustomMeshDefinition(this, new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                switch (stack.getItemDamage())
                {
                    case 1:
                        return ironLoc;
                    case 2:
                        return goldLoc;
                }
                return null;
            }
        });
    }
}
