package com.gmail.zendarva.mm.items.modules;

import com.gmail.zendarva.mm.IOType;
import com.gmail.zendarva.mm.entities.MachineFrameEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by James on 4/16/2017.
 */
public class ModuleEntityFinder extends BaseModule {
    public ModuleEntityFinder() {
        this.unlocalizedName="entityfindermodule";
        this.rfPerTick=40;
    }

    @Override
    public IOType[] requires() {
        return new IOType[]{IOType.BLOCKPOS};
    }
    public IOType[] provides() {
        return new IOType[]{IOType.ENTITY};
    }

    @Override
    public boolean tick(MachineFrameEntity entity, ItemStack module) {
        World world = entity.getWorld();
        if (!validateInput(entity))
            return false;
        BlockPos pos = (BlockPos) entity.executionStack.pop();


        List<Entity> entities= world.getEntitiesWithinAABB(Entity.class,getArea(pos));
        if (entities.isEmpty())
            return false;
        entity.executionStack.push(entities.get(0));
        return true;
    }

    @Override
    public boolean isDone(ItemStack module) {
        return true;
    }

    @Override
    public void reset(ItemStack module) {

    }

    private AxisAlignedBB getArea(BlockPos pos){
        AxisAlignedBB bb;
        bb = new AxisAlignedBB(pos).expand(1,1,1);
        return bb;
    }

}
