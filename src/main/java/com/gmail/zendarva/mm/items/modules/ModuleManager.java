package com.gmail.zendarva.mm.items.modules;

import com.gmail.zendarva.mm.items.Module;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

/**
 * Created by James on 4/16/2017.
 */
public class ModuleManager {

    private static ModuleManager myInstance;

    public HashMap<Integer, BaseModule> moduleData = new HashMap<>();
    private ModuleManager (){

    }

    public static ModuleManager instance(){
        if (myInstance == null)
            myInstance= new ModuleManager();
        return myInstance;
    }

    public void registerModule(int id, BaseModule module) {
        moduleData.put(id,module);
    }

    public BaseModule getModule(ItemStack stack){
        if (!(stack.getItem() instanceof Module))
            return null;
        if (moduleData.keySet().contains(stack.getItemDamage()))
                return moduleData.get(stack.getItemDamage());
        return null;
    }

}
