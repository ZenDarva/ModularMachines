package com.gmail.zendarva.mm.entities;

import com.gmail.zendarva.mm.OutputDir;
import com.gmail.zendarva.mm.blocks.MachineFrameBlock;
import com.gmail.zendarva.mm.items.modules.BaseModule;
import com.gmail.zendarva.mm.items.modules.ModuleManager;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.Stack;

public class MachineFrameEntity extends TileEntity implements ITickable {

    public Stack<Object> executionStack = new Stack<>();
    private int executionPointer = 0;

    ItemStackHandler accessableItems = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            MachineFrameEntity.this.markDirty();
        }
    };

    public ItemStackHandler internalGrid = new ItemStackHandler(25) {
        @Override
        protected void onContentsChanged(int slot) {
            MachineFrameEntity.this.markDirty();
        }
    };

    public MachineFrameEntity() {
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return true;
        return hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return (T) accessableItems;
        return getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("items"))
            accessableItems.deserializeNBT((NBTTagCompound) compound.getTag("items"));
        if (compound.hasKey("grid"))
            internalGrid.deserializeNBT((NBTTagCompound) compound.getTag("grid"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("items", accessableItems.serializeNBT());
        compound.setTag("grid", internalGrid.serializeNBT());
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
            executionPointer = 0;
            return;
        }

        BaseModule module = ModuleManager.instance().getModule(stack);
        boolean result = module.tick(this,stack);

        if (result && module.isDone(stack)) {
            movePointer(module.getSuccess(stack));
            module.reset(stack);
        }
        else if(!result) {
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
        if (executionPointer < 0 || executionPointer > 24)
            executionPointer =0;
    }

    private boolean isRunning() {
        return world.getBlockState(getPos()).getValue(MachineFrameBlock.stateProperty).booleanValue();
    }
}
