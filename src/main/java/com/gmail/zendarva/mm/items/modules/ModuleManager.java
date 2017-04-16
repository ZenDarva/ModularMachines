package com.gmail.zendarva.mm.items.modules;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.zendarva.mm.MM;
import net.minecraft.item.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ModuleManager {
    private static ModuleManager instance;
    File configFile;
    private ArrayList<ModuleData> moduleData;
    public static ModuleManager getInstance() {
        if (instance == null)
            instance= new ModuleManager();
        return instance;
    }

    private ModuleManager()
    {
        File configDir = MM.configDir;
        configFile = new File(configDir.getAbsolutePath() + File.separator + "modules.cfg");
        if (!configDir.exists())
        {
            configDir.mkdir();
        }
        if (!configFile.exists())
        {
            try {
                configFile.createNewFile();
                moduleData = new ArrayList<>();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        ObjectMapper om = new ObjectMapper();
        try {

            ModuleData[] data = om.readValue(configFile, ModuleData[].class);
            moduleData = new ArrayList<>(Arrays.asList(data));
        } catch (IOException e) {
            e.printStackTrace();
            moduleData = new ArrayList<>();
        }

    }

    public <T extends BaseModule> void Register( T module) {
        for(ModuleData targmodule : moduleData)
        {
            if (targmodule.name.equals(module.unlocalizedName))
                return;
        }
        moduleData.add(new ModuleData(module.unlocalizedName, moduleData.size(), module.getClass()));
        writeData();
    }

    public ArrayList<ModuleData> getModules()
    {
        return moduleData;
    }


    private void writeData()
    {
        ObjectMapper om = new ObjectMapper();
        try {
            om.writeValue(configFile, moduleData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BaseModule getModule(ItemStack stack) {
        for (ModuleData module : moduleData)
        {
            try {
                return (BaseModule)(module.clazz.newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
