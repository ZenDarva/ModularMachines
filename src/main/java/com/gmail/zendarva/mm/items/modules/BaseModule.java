package com.gmail.zendarva.mm.items.modules;

import com.gmail.zendarva.mm.IOType;
import com.gmail.zendarva.mm.OutputDir;
import com.gmail.zendarva.mm.entities.MachineFrameEntity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BaseModule {
    public IOType[] requires(){ return new IOType[]{IOType.ANY};};
    public int rfPerTick;
    public int progress;
    public String unlocalizedName;
    public boolean blocking = false;



    public void setSuccess(ItemStack stack, OutputDir dir){
        validateCompound(stack);
        stack.getTagCompound().setString("success",dir.name());
    }
    public OutputDir getSuccess(ItemStack stack){
        validateCompound(stack);
        String value = stack.getTagCompound().getString("success");
        if (value == null || value == "") {
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
        if (value == null || value == "") {
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


    public abstract boolean tick(MachineFrameEntity entity, ItemStack module);
    public abstract boolean isDone(ItemStack module);
    public abstract void reset(ItemStack module);

    public void saveNBT(ItemStack stack) {
        NBTTagCompound tag = stack.getTagCompound();
        tag.setInteger("progress", progress);
    }
    public void loadNBT(ItemStack stack) {
        NBTTagCompound tag = stack.getTagCompound();
        progress= tag.getInteger("progress");
    }

    public boolean validateInput(MachineFrameEntity entity) {
        if (entity.executionStack.size() < requires().length)
            return false;
        int count = 0;
        for (IOType type : requires())
        {
            switch(type){
                case ANY:
                    break;
                case BLOCKPOS:
                    if (!(entity.executionStack.get(count) instanceof BlockPos))
                        return false;
                    break;
                case ENTITYLIVING:
                    if (!(entity.executionStack.get(count) instanceof EntityLiving))
                        return false;
                        break;
                case ITEMSTACK:
                    if (!(entity.executionStack.get(count) instanceof ItemStack))
                        return false;
                        break;
            }
            count++;
        }
        return true;
    }

    public EnumActionResult onUse(ItemStack stack, EntityPlayer player, World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ){
        return EnumActionResult.PASS;
    }

    protected void writeBlockPos(NBTTagCompound tag, String name, BlockPos pos) {
        if (pos == null) {
            tag.setTag(name, new NBTTagCompound());
            return;
        }
        NBTTagCompound posTag = new NBTTagCompound();
        posTag.setInteger("x", pos.getX());
        posTag.setInteger("y", pos.getY());
        posTag.setInteger("z", pos.getZ());
        tag.setTag(name, posTag);
    }

    protected BlockPos readBlockPos(NBTTagCompound tag, String name) {
        NBTTagCompound posTag = tag.getCompoundTag(name);
        if (posTag.hasNoTags())
            return null;
        return new BlockPos(posTag.getInteger("x"), posTag.getInteger("y"), posTag.getInteger("z"));
    }
}
