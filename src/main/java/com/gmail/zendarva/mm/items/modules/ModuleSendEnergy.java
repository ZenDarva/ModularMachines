package com.gmail.zendarva.mm.items.modules;

import com.gmail.zendarva.mm.IOType;
import com.gmail.zendarva.mm.entities.MachineFrameEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;

/**
 * Created by James on 4/18/2017.
 */
public class ModuleSendEnergy extends BaseModule {
    @Override
    public IOType[] requires() {
        return new IOType[]{IOType.BLOCKPOS};
    }

    public ModuleSendEnergy() {
        this.unlocalizedName="sendenergymodule";
        this.rfPerTick=10;
    }

    @Override
    public boolean tick(MachineFrameEntity entity, ItemStack module) {
        if (!validateInput(entity))
            return false;
        BlockPos target = (BlockPos) entity.executionStack.pop();
        World world = entity.getWorld();
        TileEntity targEntity = world.getTileEntity(target);
        if (targEntity == null)
            return false;
        if (!targEntity.hasCapability(CapabilityEnergy.ENERGY, EnumFacing.UP))
            return false;
        int energy = entity.energyStorage.extractEnergy(500,false);
        targEntity.getCapability(CapabilityEnergy.ENERGY,EnumFacing.UP).receiveEnergy(energy,false);
        if (energy == 0)
            return false;
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
