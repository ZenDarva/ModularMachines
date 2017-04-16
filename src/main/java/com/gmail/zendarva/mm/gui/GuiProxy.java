package com.gmail.zendarva.mm.gui;

import com.gmail.zendarva.mm.blocks.MachineFrameBlock;
import com.gmail.zendarva.mm.container.MachineFrameContainer;
import com.gmail.zendarva.mm.entities.MachineFrameEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiProxy implements IGuiHandler {
    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x,y,z);


        switch(ID) {
            case MachineFrameBlock.GUI_ID:
                TileEntity entity = world.getTileEntity(pos);
                if (entity instanceof MachineFrameEntity){
                    return new MachineFrameContainer(player.inventory, (MachineFrameEntity) entity);
                }


        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x,y,z);
        switch(ID) {
            case MachineFrameBlock.GUI_ID:
                TileEntity entity = world.getTileEntity(pos);
                if (entity instanceof MachineFrameEntity){
                    return new MachineFrameGui((MachineFrameEntity) entity, new MachineFrameContainer(player.inventory, (MachineFrameEntity) entity));
                }


        }
        return null;
    }
}
