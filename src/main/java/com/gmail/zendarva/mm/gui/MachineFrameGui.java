package com.gmail.zendarva.mm.gui;

import com.gmail.zendarva.mm.MM;
import com.gmail.zendarva.mm.OutputDir;
import com.gmail.zendarva.mm.entities.MachineFrameEntity;
import com.gmail.zendarva.mm.items.modules.BaseModule;
import com.gmail.zendarva.mm.items.modules.ModuleManager;
import com.gmail.zendarva.mm.network.OutputDirUpdatePacket;
import com.gmail.zendarva.mm.network.PacketHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;

public class MachineFrameGui extends GuiContainer {
    MachineFrameEntity entity;
    HashMap<Integer, ButtonLocation> buttonData = new HashMap<>();

    public MachineFrameGui(MachineFrameEntity entity, Container inventorySlotsIn) {
        super(inventorySlotsIn);
        this.entity=entity;
        xSize =WIDTH;
        ySize=HEIGHT;
    }

    public static final int WIDTH = 226;
    public static final int HEIGHT = 224;

    @Override
    public void initGui() {
        super.initGui();
        int index = 0;
        for (int x = 0; x< 5; x++){
            for (int y=0;y<5;y++){
                buttonData.put(index, new ButtonLocation(guiLeft + startX + x * 23 + 27,guiTop +20 + y*23,entity,index));
                if (entity.internalGrid.getStackInSlot(index) != ItemStack.EMPTY)
                {
                    ItemStack stack = entity.internalGrid.getStackInSlot(index);
                    BaseModule module = ModuleManager.instance().getModule(stack);
                }
                index++;
            }
        }
    }

    private int startX = 30;
    private int startY = 144;
    private static final ResourceLocation background = new ResourceLocation(MM.MODID, "textures/gui/frame_gui.png");

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft,guiTop,0,0,xSize,ySize);

        for (int x = 0; x< 9;x++)
        {
            for (int y =0; y<3;y++)
            {
                drawTexturedModalRect(guiLeft + startX + x * 18, guiTop + startY +y * 18, 12, 236, 17, 17);
            }
        }
        for (int x = 0; x< 9;x++)
        {
            for (int y =0; y<3;y++)
            {
                drawTexturedModalRect(guiLeft + startX + x * 18, guiTop + startY +58, 12, 236, 17, 17);
            }
        }

        //Input
        drawTexturedModalRect(guiLeft + 10, guiTop + 60, 12, 236, 17, 17);
        //Output
        drawTexturedModalRect(guiLeft + 190, guiTop + 60, 12, 236, 17, 17);

        //Configuration Floor.
        for (int x = 0; x< 5; x++){
            for (int y=0;y<5;y++){
                drawTexturedModalRect(guiLeft + startX + x * 23 + 27, guiTop +20 + y*23, 50, 233, 23, 23);
            }
        }
        buttonData.values().forEach(f->f.draw(this));
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        buttonData.keySet().forEach(f->{
            if (buttonData.get(f).intersect(mouseX,mouseY))
                buttonData.get(f).setOutput(mouseX,mouseY);
                }
        );
        super.mouseClicked(mouseX,mouseY,mouseButton);
    }

    private class ButtonLocation{
        private final MachineFrameEntity owner;
        private final int slotId;
        Rectangle rect;

        public ButtonLocation(int x, int y, MachineFrameEntity owner, int slotId)
        {
            this.owner = owner;
            this.slotId = slotId;
            rect = new Rectangle(x,y,22,22);
        }

        public boolean intersect(int x, int y)
        {
            if (x >= rect.x && x <= rect.x+rect.width &&
                    y >= rect.y && y <= rect.y+rect.height)
                return true;
            return false;
        }

        public void setOutput(int x, int y)
        {
            ItemStack stack =owner.internalGrid.getStackInSlot(slotId);
            BaseModule module = ModuleManager.instance().getModule(stack);
            if (stack == ItemStack.EMPTY)
                return;

            OutputDir dir= OutputDir.none;
            if (x >= rect.x && x<= rect.x+2)
                dir = OutputDir.left;
            if (x >= rect.x + rect.width-2 && x <= rect.x+rect.width)
                dir = OutputDir.right;
            if (y >= rect.y && y<= rect.y+2)
                dir = OutputDir.up;
            if (y >= rect.y + rect.height-2 && y <= rect.y+rect.height)
                dir = OutputDir.down;

            if (module.getSuccess(stack) == dir) {
                module.setFailure(stack,dir);
                PacketHandler.INSTANCE.sendToServer(new OutputDirUpdatePacket(slotId,dir,false,entity.getPos()));
                module.setSuccess(stack,OutputDir.none);
                PacketHandler.INSTANCE.sendToServer(new OutputDirUpdatePacket(slotId,OutputDir.none,true,entity.getPos()));
                entity.markDirty();
                return;
            }
            if (module.getFailure(stack) == dir)
            {
                module.setFailure(stack,OutputDir.none);
                PacketHandler.INSTANCE.sendToServer(new OutputDirUpdatePacket(slotId,dir,false,entity.getPos()));
                return;
            }
            module.setSuccess(stack,dir);

            PacketHandler.INSTANCE.sendToServer(new OutputDirUpdatePacket(slotId,dir,true,entity.getPos()));
        }

        public void draw(GuiContainer container)
        {
            ItemStack stack =owner.internalGrid.getStackInSlot(slotId);
            if (stack== ItemStack.EMPTY)
                return;
            BaseModule module = ModuleManager.instance().getModule(stack);
            switch(module.getSuccess(stack))
            {
                case up:
                    container.drawTexturedModalRect(rect.x,rect.y,87,243,23,2);
                    break;
                case down:
                    container.drawTexturedModalRect(rect.x,rect.y+rect.height-2,87,237,23,2);
                    break;
                case left:
                    container.drawTexturedModalRect(rect.x,rect.y,85,233,2,23);
                    break;
                case right:
                    container.drawTexturedModalRect(rect.x+rect.width-2,rect.y,79,233,2,23);
                    break;
            }
            switch(module.getFailure(stack)){
                case up:
                    container.drawTexturedModalRect(rect.x,rect.y,87,240,23,2);
                    break;
                case down:
                    container.drawTexturedModalRect(rect.x,rect.y+rect.height-2,87,234,23,2);
                    break;
                case left:
                    container.drawTexturedModalRect(rect.x,rect.y,82,233,2,23);
                    break;
                case right:
                    container.drawTexturedModalRect(rect.x+rect.width-2,rect.y,76,233,2,23);
                    break;
            }

        }
    }


}
