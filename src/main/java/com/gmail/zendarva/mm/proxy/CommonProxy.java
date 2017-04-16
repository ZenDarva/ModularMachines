package com.gmail.zendarva.mm.proxy;

import com.gmail.zendarva.mm.MM;
import com.gmail.zendarva.mm.ModItems;
import com.gmail.zendarva.mm.gui.GuiProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        ModItems.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(MM.instance,new GuiProxy());
    }

    public void init(FMLInitializationEvent e) {
    }

    public void postInit(FMLPostInitializationEvent e) {

    }
}
