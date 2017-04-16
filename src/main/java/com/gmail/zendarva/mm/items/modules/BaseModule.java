package com.gmail.zendarva.mm.items.modules;

import com.gmail.zendarva.mm.IOType;
import com.gmail.zendarva.mm.OutputDir;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;

public abstract class BaseModule {
    public IOType requires(){ return IOType.None;};
    public IOType provides(){ return IOType.None;};
    public abstract int rfPerTick();
    public abstract int progress();
    public String unlocalizedName;

    public void setSuccess(ItemStack stack, OutputDir dir){
        validateCompound(stack);
        stack.getTagCompound().setString("success",dir.name());
    }
    public OutputDir getSuccess(ItemStack stack){
        validateCompound(stack);
        String value = stack.getTagCompound().getString("success");
        if (value == null) {
            setSuccess(stack, OutputDir.none);
            value = "none";
        }
        return OutputDir.valueOf(value);
    }
    public void setFailure(ItemStack stack, OutputDir dir){
        validateCompound(stack);
        stack.getTagCompound().setString("failure",dir.name());
    }
    public OutputDir getFailure(ItemStack stack){
        validateCompound(stack);
        String value = stack.getTagCompound().getString("failure");
        if (value == null) {
            setFailure(stack, OutputDir.none);
            value = "none";
        }
        return OutputDir.valueOf(value);
    }

    private void validateCompound(ItemStack stack) {
        if (stack.hasTagCompound() == false)
        {
            stack.setTagCompound(new NBTTagCompound());
        }
    }
}
