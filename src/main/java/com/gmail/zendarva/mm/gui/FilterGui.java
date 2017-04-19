package com.gmail.zendarva.mm.gui;

import com.gmail.zendarva.mm.MM;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.EntityLiving;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.GuiErrorBase;

/**
 * Created by James on 4/17/2017.
 */
public class FilterGui extends GuiContainer {

    public final static int GUI_ID=3;
    private static final ResourceLocation background = new ResourceLocation(MM.MODID, "textures/gui/filtergui.png");
    private int startX = 9;
    private int startY = 83;


    public FilterGui(Container inventorySlotsIn) {
        super(inventorySlotsIn);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft,guiTop,0,0,xSize,ySize);


        for (int x = 0; x< 9;x++)
        {
            for (int y =0; y<3;y++)
            {
                drawTexturedModalRect(guiLeft + startX + x * 18, guiTop + startY +y * 18, 3, 183, 17, 17);
            }
        }
        for (int x = 0; x< 9;x++)
        {
            for (int y =0; y<3;y++)
            {
                drawTexturedModalRect(guiLeft + startX + x * 18, guiTop + startY +58, 3, 183, 17, 17);
            }
        }
    }


}
