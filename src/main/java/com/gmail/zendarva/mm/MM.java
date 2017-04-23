package com.gmail.zendarva.mm;

import com.gmail.zendarva.mm.network.PacketHandler;
import com.gmail.zendarva.mm.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import java.io.File;


@Mod(modid = MM.MODID, version = MM.VERSION)
public class MM {
    public static final String VERSION = "1.0.2";
    public static final String MODID = "modularmachines";
    public static final PacketHandler packetHandler = new PacketHandler();


    public static File configDir;
    @SidedProxy(clientSide = "com.gmail.zendarva.mm.proxy.ClientProxy", serverSide = "com.gmail.zendarva.mm.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static MM instance;

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();

        configDir = new File(event.getModConfigurationDirectory().getAbsolutePath() + File.separator + "mm");

        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }
}
