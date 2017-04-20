package com.gmail.zendarva.mm.entities;

import com.gmail.zendarva.mm.ModularEnergyStorage;
import com.gmail.zendarva.mm.OutputDir;
import com.gmail.zendarva.mm.blocks.MachineFrameBlock;
import com.gmail.zendarva.mm.items.modules.BaseModule;
import com.gmail.zendarva.mm.items.modules.ModuleManager;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.Stack;

public class MachineFrameEntity extends TileEntity implements ITickable {

    public Stack<Object> executionStack = new Stack<>();
    private int executionPointer = 0;

    public ModularEnergyStorage energyStorage = new ModularEnergyStorage(1000000){
        @Override
        public int receiveEnergy(int maxReceive, boolean simulate) {
            MachineFrameEntity.this.markDirty();
            return super.receiveEnergy(maxReceive,simulate);
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate) {
            MachineFrameEntity.this.markDirty();
            return super.extractEnergy(maxExtract, simulate);
        }
    };


    ItemStackHandler accessableItems = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            MachineFrameEntity.this.markDirty();
        }

    };

    @Override
    public void markDirty() {
        if (!this.getWorld().isRemote)
            world.notifyBlockUpdate(pos,world.getBlockState(pos),world.getBlockState(pos),3);
        super.markDirty();
    }

    public ItemStackHandler internalGrid = new ItemStackHandler(25) {
        @Override
        protected void onContentsChanged(int slot) {
            MachineFrameEntity.this.markDirty();
        }
    };

    public MachineFrameEntity() {
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound tag = new NBTTagCompound();
        writeToNBT(tag);
        SPacketUpdateTileEntity packet = new SPacketUpdateTileEntity(pos,1,tag);
        return packet;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        this.readFromNBT(tag);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound tag = new NBTTagCompound();
        writeToNBT(tag);
        return tag;

    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return true;
        if (capability == CapabilityEnergy.ENERGY)
            return true;
        return hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return (T) accessableItems;
        if (capability == CapabilityEnergy.ENERGY)
            return (T) energyStorage;
        return getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("items"))
            accessableItems.deserializeNBT((NBTTagCompound) compound.getTag("items"));
        if (compound.hasKey("grid"))
            internalGrid.deserializeNBT((NBTTagCompound) compound.getTag("grid"));
        if (compound.hasKey("energy"))
            energyStorage.setEnergy(compound.getInteger("energy"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("items", accessableItems.serializeNBT());
        compound.setTag("grid", internalGrid.serializeNBT());
        compound.setInteger("energy", energyStorage.getEnergyStored());
        return compound;
    }


    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return false;
    }

    @Override
    public void update() {
        if (world.isRemote)
            return;
        if(!isRunning())
            return;


        ItemStack stack = internalGrid.getStackInSlot(executionPointer);
        if (stack == ItemStack.EMPTY) {
            clearStack();
            executionPointer = 0;
            return;
        }

        BaseModule module = ModuleManager.instance().getModule(stack);
        boolean result = module.tick(this,stack);

        if (result && module.isDone(stack)) {
            movePointer(module.getSuccess(stack));
            module.reset(stack);
        }
        else if(!result && !module.blocking) {
            movePointer(module.getFailure(stack));
            module.reset(stack);
        }
    }


    public void movePointer(OutputDir dir) {
        switch (dir) {
            case down:
                executionPointer++;
                break;
            case up:
                executionPointer--;
                break;
            case left:
                executionPointer -= 5;
                break;
            case right:
                executionPointer += 5;
                break;
            case none:
                executionPointer = 0;
        }
        if (executionPointer < 0 || executionPointer > 24) {
            clearStack();
            executionPointer = 0;
        }
    }

    private boolean isRunning() {
        return world.getBlockState(getPos()).getValue(MachineFrameBlock.stateProperty).booleanValue();
    }


    private void clearStack()
    {
        while(!executionStack.isEmpty())
        {
            Object obj = executionStack.pop();
            if (obj instanceof ItemStack) {
                EntityItem entity = new EntityItem(world,pos.getX(),pos.getY()+1,pos.getZ(), (ItemStack) obj);
                world.spawnEntity(entity);
            }
        }
    }
}
