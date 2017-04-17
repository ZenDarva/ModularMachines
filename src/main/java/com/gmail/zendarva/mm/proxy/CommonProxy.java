package com.gmail.zendarva.mm.proxy;

import com.gmail.zendarva.mm.MM;
import com.gmail.zendarva.mm.ModItems;
import com.gmail.zendarva.mm.gui.GuiProxy;
import com.gmail.zendarva.mm.network.PacketHandler;
import com.gmail.zendarva.mm.recipie.ModRecipies;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        ModItems.init();

        NetworkRegistry.INSTANCE.registerGuiHandler(MM.instance,new GuiProxy());
        PacketHandler.registerMessages("MM");
    }

    public void init(FMLInitializationEvent e) {
        ModRecipies.init();
    }

    public void postInit(FMLPostInitializationEvent e) {

    }
}
