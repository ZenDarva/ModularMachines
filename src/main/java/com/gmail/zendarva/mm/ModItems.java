package com.gmail.zendarva.mm;

import com.gmail.zendarva.mm.blocks.MachineFrameBlock;
import com.gmail.zendarva.mm.items.Module;
import com.gmail.zendarva.mm.items.modules.InputModule;
import com.gmail.zendarva.mm.items.modules.ModuleManager;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by James on 11/14/2016.
 */
public class ModItems {

    public static MachineFrameBlock machineFrameBlock;


    public static Module module;

    public static void init()
    {
        machineFrameBlock = new MachineFrameBlock(Material.ANVIL);

        ModuleManager.getInstance().Register(new InputModule());
        ModuleManager.getInstance();

        Module module = new Module();
    }

    @SideOnly(Side.CLIENT)
    public static void initModels()
    {
        machineFrameBlock.initModel();

    }

}
