package com.gmail.zendarva.mm.items.modules;

import com.gmail.zendarva.mm.IOType;
import com.gmail.zendarva.mm.entities.MachineFrameEntity;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by James on 4/17/2017.
 */
public class ModuleTeleport extends BaseModule {

    public ModuleTeleport() {
        this.unlocalizedName="teleportmodule";this.rfPerTick=100;
    }

    @Override
    public IOType[] requires() {
        return new IOType[]{IOType.ENTITY, IOType.BLOCKPOS};
    }

    @Override
    public boolean tick(MachineFrameEntity entity, ItemStack module) {
        if (!validateInput(entity))
            return false;
        BlockPos targPos = (BlockPos) entity.executionStack.pop();
        Entity targEntity = (Entity) entity.executionStack.pop();
        Random rand = new Random(System.currentTimeMillis());

        World world = entity.getWorld();

        for (int i = 0; i < 32; ++i)
        {
            world.spawnParticle(EnumParticleTypes.PORTAL, entity.getPos().getX(), entity.getPos().getY() + rand.nextDouble() * 2.0D, entity.getPos().getZ(), rand.nextGaussian(), 0.0D, rand.nextGaussian(), new int[0]);
        }
        BlockPos safePos = findSafePos(world,targPos);
        targEntity.setPositionAndUpdate(safePos.getX(),safePos.getY(),safePos.getZ());

        return true;
    }

    @Override
    public boolean isDone(ItemStack module) {
        return true;
    }

    @Override
    public void reset(ItemStack module) {

    }

    private BlockPos findSafePos(World world, BlockPos pos)
    {
        if (!world.isAirBlock(pos))
        {
            pos = pos.up();
        }
        if (world.isAirBlock(pos) && world.isAirBlock(pos.up())){
            return pos;
        }
        return findSafePos(world,pos);
    }
}
