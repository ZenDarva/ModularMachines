package com.gmail.zendarva.mm;

import com.gmail.zendarva.mm.blocks.MachineFrameBlock;
import com.gmail.zendarva.mm.items.ItemDust;
import com.gmail.zendarva.mm.items.Module;
import com.gmail.zendarva.mm.items.modules.*;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by James on 11/14/2016.
 */
public class ModItems {

    public static MachineFrameBlock machineFrameBlock;
    public static Module module;
    public static ItemDust dust;


    public static void init()
    {
        machineFrameBlock = new MachineFrameBlock(Material.ANVIL);

        ModuleManager.instance().registerModule(1,new InputModule());
        ModuleManager.instance().registerModule(2,new OutputModule());
        ModuleManager.instance().registerModule(3,new FurnaceModule());
        ModuleManager.instance().registerModule(4,new ModuleCrusher());
        ModuleManager.instance().registerModule(5,new ModulePipe());
        ModuleManager.instance().registerModule(6,new ModuleLocation());
        ModuleManager.instance().registerModule(7,new ModuleBreak());
        ModuleManager.instance().registerModule(8,new ModuleBlock());
        ModuleManager.instance().registerModule(9,new ModuleMobFinder());
        ModuleManager.instance().registerModule(10,new ModuleKiller());
        ModuleManager.instance().registerModule(11,new ModuleItemFinder());

        module = new Module();
        dust = new ItemDust();
    }

    @SideOnly(Side.CLIENT)
    public static void initModels()
    {
        machineFrameBlock.initModel();
        module.initModel();
    }

}
