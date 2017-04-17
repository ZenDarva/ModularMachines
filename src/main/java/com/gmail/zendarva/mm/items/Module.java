package com.gmail.zendarva.mm.items;

import com.gmail.zendarva.mm.MM;
import com.gmail.zendarva.mm.items.modules.BaseModule;
import com.gmail.zendarva.mm.items.modules.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Module extends Item {

    HashMap<Integer, ModelResourceLocation> locs = new HashMap<>();

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
        int damage =stack.getItemDamage();
        HashMap<Integer, BaseModule> moduleData = ModuleManager.instance().moduleData;
        if (moduleData.keySet().contains(damage))
            return ModuleManager.instance().moduleData.get(stack.getItemDamage()).unlocalizedName;
        return "Unknown Item";
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
        HashMap<Integer, BaseModule> moduleData = ModuleManager.instance().moduleData;

        moduleData.keySet().forEach(f-> {
            subItems.add(new ItemStack(this,1,f));
        });

    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
        super.onCreated(stack, worldIn, playerIn);
        stack.setTagCompound(new NBTTagCompound());
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        HashMap<Integer, BaseModule> moduleData = ModuleManager.instance().moduleData;

        moduleData.keySet().forEach(f-> {
            locs.put(f,new ModelResourceLocation(MM.MODID+":"+moduleData.get(f).unlocalizedName,"inventory"));
        });

        ModelBakery.registerItemVariants(this, locs.values().toArray(new ResourceLocation[locs.size()]));

        ModelLoader.setCustomMeshDefinition(this, new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                return (ModelResourceLocation) locs.get(stack.getItemDamage());
            }
        });
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItemMainhand();
        BaseModule module = ModuleManager.instance().getModule(stack);
        return module.onUse(stack, player, worldIn, pos,facing, hitX, hitY, hitZ);
    }


}
