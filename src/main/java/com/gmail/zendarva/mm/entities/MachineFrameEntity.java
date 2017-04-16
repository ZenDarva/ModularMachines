package com.gmail.zendarva.mm.entities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class MachineFrameEntity extends TileEntity {

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

    public MachineFrameEntity(){
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
            internalGrid.deserializeNBT((NBTTagCompound)compound.getTag("grid"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("items", accessableItems.serializeNBT());
        compound.setTag("grid",internalGrid.serializeNBT());
        return compound;
    }
}
