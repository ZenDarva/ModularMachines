package com.gmail.zendarva.mm.items.modules;

import com.gmail.zendarva.mm.entities.MachineFrameEntity;
import jdk.nashorn.internal.ir.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by James on 4/16/2017.
 */
public class ModuleBreak extends BaseModule {
    public ModuleBreak() {
        this.unlocalizedName = "breakmodule";
    }

    @Override
    public boolean tick(MachineFrameEntity entity, ItemStack module) {
        BlockPos target = readBlockPos(module.getTagCompound(),"target");
        if (target == null) {
            validateInput(entity);
            target = (BlockPos) entity.executionStack.pop();
        }

        World world = entity.getWorld();
        world.sendBlockBreakProgress(1,target,progress/10);
        progress+=2;
        if (progress >= 100)
        {
            List<ItemStack> drops = world.getBlockState(target).getBlock().getDrops(world,target,world.getBlockState(target),0);
            world.setBlockToAir(target);
            drops.forEach(f->{
                entity.executionStack.push(f.copy());
            });
        }
        saveNBT(module,target);
        return true;
    }

    protected void saveNBT(ItemStack module, BlockPos pos){
        writeBlockPos(module.getTagCompound(),"target",pos);
        saveNBT(module);
    }
    @Override
    public boolean isDone(ItemStack module) {
        return progress > 100;
    }

    @Override
    public void reset(ItemStack module) {
        progress =0;
        saveNBT(module,null);

    }
}
