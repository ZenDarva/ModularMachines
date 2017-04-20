package com.gmail.zendarva.mm.items.modules;

import com.gmail.zendarva.mm.IOType;
import com.gmail.zendarva.mm.entities.MachineFrameEntity;
import net.minecraft.client.Minecraft;
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
public class ModuleLocation extends BaseModule {
    public ModuleLocation() {
        this.unlocalizedName = "locationmodule";
        this.rfPerTick=10;
    }
    public IOType[] provides() {
        return new IOType[]{IOType.BLOCKPOS};
    }
    @Override
    public boolean tick(MachineFrameEntity entity, ItemStack module) {
        if (entity.getWorld().isRemote)
            return true;
        validateInput(entity);
        BlockPos corner1 = getCorner1(module);
        BlockPos corner2 = getCorner2(module);
        BlockPos curPos = getCurrent(module);

        if (corner1 == null || corner2 == null)
            return false;

        locationDisambig ld = new locationDisambig(corner1,corner2);
        if (curPos == null) {
            curPos = new BlockPos(ld.leastX,ld.leastY,ld.leastZ);
            entity.executionStack.push(curPos.toImmutable());
            saveNBT(module,corner1,corner2,curPos);
            return true;
        }

        if (curPos.getX() < ld.maxX) {
            curPos = curPos.east();
            entity.executionStack.push(curPos.toImmutable());
            saveNBT(module,corner1,corner2,curPos);
            return true;
        }
        if (curPos.getZ() <ld.maxZ){
            curPos = curPos.south();
            curPos = curPos.add((ld.maxX-ld.leastX) *-1,0,0);
            entity.executionStack.push(curPos.toImmutable());
            saveNBT(module,corner1,corner2,curPos);
            return true;
        }
        if (curPos.getY() <ld.maxY){
            curPos = curPos.up();
            curPos = curPos.add((ld.maxX-ld.leastX) *-1,0,(ld.maxZ-ld.leastZ) *-1);
            entity.executionStack.push(curPos.toImmutable());
            saveNBT(module,corner1,corner2,curPos);
            return true;
        }

        curPos = new BlockPos(ld.leastX,ld.leastY,ld.leastZ);

        entity.executionStack.push(curPos.toImmutable());
        saveNBT(module,corner1,corner2,curPos);


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

        if (player.isSneaking()) {
            stack.setTagCompound(new NBTTagCompound());
            player.sendMessage(new TextComponentString("Location cleared"));
            return EnumActionResult.SUCCESS;
        }

        BlockPos corner1 = getCorner1(stack);
        BlockPos corner2 = getCorner2(stack);
        if (corner1 == null) {
            corner1 = pos;
            if (worldIn.isRemote) {
                player.sendMessage(new TextComponentString(String.format("Corner 1: %d %d %d", pos.getX(), pos.getY(), pos.getZ())));
            }
            saveNBT(stack, corner1, null, null);
            return EnumActionResult.SUCCESS;
        }
        if (corner2 == null) {
            corner2 = pos;
            if (worldIn.isRemote) {
                player.sendMessage(new TextComponentString(String.format("Corner 2: %d %d %d", pos.getX(), pos.getY(), pos.getZ())));
            }
            saveNBT(stack, corner1, corner2, null);
            return EnumActionResult.SUCCESS;
        }

        return EnumActionResult.PASS;

    }


    public void saveNBT(ItemStack stack, BlockPos corner1, BlockPos corner2, BlockPos current) {
        NBTTagCompound tag = stack.getTagCompound();
        writeBlockPos(tag, "corner1", corner1);
        writeBlockPos(tag, "corner2", corner2);
        writeBlockPos(tag, "current", current);

        saveNBT(stack);
    }

    public BlockPos getCorner1(ItemStack stack) {
        return readBlockPos(stack.getTagCompound(), "corner1");
    }

    public BlockPos getCorner2(ItemStack stack) {
        return readBlockPos(stack.getTagCompound(), "corner2");
    }

    public BlockPos getCurrent(ItemStack stack) {
        return readBlockPos(stack.getTagCompound(), "current");
    }



    private class locationDisambig{
        public int leastX,leastY,leastZ;
        public int maxX,maxY,maxZ;

        public locationDisambig(BlockPos first, BlockPos second)
        {
            if (first.getX() < second.getX()) {
                leastX = first.getX();
                maxX = second.getX();
            }
            else
            {
                leastX = second.getX();
                maxX = first.getX();
            }
            if (first.getY() < second.getY()) {
                leastY = first.getY();
                maxY = second.getY();
            }
            else
            {
                leastY = second.getY();
                maxY = first.getY();
            }

            if (first.getZ() < second.getZ()) {
                leastZ = first.getZ();
                maxZ = second.getZ();
            }
            else
            {
                leastZ = second.getZ();
                maxZ = first.getZ();
            }
        }
    }
}
