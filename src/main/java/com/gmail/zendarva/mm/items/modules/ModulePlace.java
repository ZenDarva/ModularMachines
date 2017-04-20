package com.gmail.zendarva.mm.items.modules;

import com.gmail.zendarva.mm.IOType;
import com.gmail.zendarva.mm.entities.MachineFrameEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by James on 4/16/2017.
 */
public class ModulePlace extends BaseModule{
    public ModulePlace() {
        this.unlocalizedName="placemodule";
        this.blocking = true;
        this.rfPerTick=100;
    }

    @Override
    public IOType[] requires() {
        return new IOType[]{IOType.ITEMSTACK,IOType.BLOCKPOS};
    }

    @Override
    public boolean tick(MachineFrameEntity entity, ItemStack module) {
        if (!validateInput(entity))
            return false;
        BlockPos pos = (BlockPos) entity.executionStack.pop();
        ItemStack stack = (ItemStack) entity.executionStack.pop();
        World world = entity.getWorld();

        if (!world.isAirBlock(pos)){
            entity.executionStack.push(stack);
            entity.executionStack.push(pos);
            return false;
        }
        Block block = Block.getBlockFromItem(stack.getItem());
        if (block == null){
            //entity.executionStack.push(stack);
            EntityItem newEntity = new EntityItem(world,pos.getX(),pos.getY()+1,pos.getZ(), stack);
            world.spawnEntity(newEntity);
            return true;
        }
        System.out.println(world.isRemote);
        world.setBlockState(pos,block.getDefaultState(),2);
        world.setBlockState(pos,block.getDefaultState());
        world.scheduleUpdate(pos,block,2);

        return true;

    }

    @Override
    public boolean isDone(ItemStack module) {
        return true;
    }

    @Override
    public void reset(ItemStack module) {

    }
}
