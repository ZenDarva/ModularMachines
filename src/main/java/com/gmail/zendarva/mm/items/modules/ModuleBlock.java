package com.gmail.zendarva.mm.items.modules;

import com.gmail.zendarva.mm.entities.MachineFrameEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

/**
 * Created by James on 4/16/2017.
 */
public class ModuleBlock extends BaseModule{
    public ModuleBlock() {
        this.unlocalizedName="blockmodule";
    }

    @Override
    public boolean tick(MachineFrameEntity entity, ItemStack module) {
        BlockPos target = readBlockPos(module.getTagCompound(),"target");
        if (target == null)
            return false;
        entity.executionStack.push(target);
        return true;
    }

    @Override
    public boolean isDone(ItemStack module) {
        return true;
    }

    @Override
    public void reset(ItemStack module) {

    }

    @Override
    public EnumActionResult onUse(ItemStack stack, EntityPlayer player, World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (stack.getTagCompound() == null) {
            stack.setTagCompound(new NBTTagCompound());
        }
        writeBlockPos(stack.getTagCompound(),"target",pos);
        player.sendMessage(new TextComponentString(String.format("Block Set: %d %d %d", pos.getX(), pos.getY(), pos.getZ())));
        return EnumActionResult.SUCCESS;
    }
}
