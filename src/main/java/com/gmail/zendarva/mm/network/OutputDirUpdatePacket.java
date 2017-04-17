package com.gmail.zendarva.mm.network;

import com.gmail.zendarva.mm.OutputDir;
import com.gmail.zendarva.mm.entities.MachineFrameEntity;
import com.gmail.zendarva.mm.items.modules.BaseModule;
import com.gmail.zendarva.mm.items.modules.ModuleManager;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by James on 4/16/2017.
 */
public class OutputDirUpdatePacket implements IMessage {

    private int slot;
    private OutputDir dir;
    private boolean success;
    private BlockPos pos;


    public OutputDirUpdatePacket(){};
    public OutputDirUpdatePacket(int slot, OutputDir dir, boolean success, BlockPos pos){

        this.slot = slot;
        this.dir = dir;
        this.success = success;
        this.pos = pos;
    }
    @Override
    public void fromBytes(ByteBuf buf) {
        this.slot=buf.readInt();
        this.dir=OutputDir.values()[buf.readInt()];
        this.success=buf.readBoolean();
        this.pos=new BlockPos(buf.readInt(),buf.readInt(),buf.readInt());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(slot);
        buf.writeInt(dir.ordinal());
        buf.writeBoolean(success);
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
    }

    public static class Handler implements IMessageHandler<OutputDirUpdatePacket, IMessage> {

        @Override
        public IMessage onMessage(OutputDirUpdatePacket message, MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(()->handle(message,ctx));
            return null;
        }

        private void handle(OutputDirUpdatePacket packet, MessageContext ctx){
            EntityPlayerMP player =ctx.getServerHandler().player;
            World world = player.world;
            TileEntity te = world.getTileEntity(packet.pos);
            if (!(te instanceof MachineFrameEntity))
                return;
            ItemStack stack = ((MachineFrameEntity) te).internalGrid.getStackInSlot(packet.slot);
            if (stack == ItemStack.EMPTY) //Somethings fucky...
                return;
            BaseModule module = ModuleManager.instance().getModule(stack);
            if (module == null) //Fuckier and Fuckier...
                return;
            if (packet.success)
                module.setSuccess(stack,packet.dir);
            else
                module.setFailure(stack,packet.dir);

        }
    }
}
