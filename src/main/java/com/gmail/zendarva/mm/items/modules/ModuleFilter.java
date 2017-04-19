package com.gmail.zendarva.mm.items.modules;

import com.gmail.zendarva.mm.IOType;
import com.gmail.zendarva.mm.MM;
import com.gmail.zendarva.mm.entities.MachineFrameEntity;
import com.gmail.zendarva.mm.gui.FilterGui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by James on 4/17/2017.
 */
public class ModuleFilter extends BaseModule {
    @Override
    public IOType[] requires() {
        return new IOType[]{IOType.ANY};
    }

    public ModuleFilter() {
        this.unlocalizedName="filtermodule";
    }

    @Override
    public boolean tick(MachineFrameEntity entity, ItemStack module) {
        if (!validateInput(entity))
            return false;

        return false;
    }

    @Override
    public boolean isDone(ItemStack module) {
        return false;
    }

    @Override
    public void reset(ItemStack module) {

    }

    @Override
    public ActionResult<ItemStack> onRightClick(ItemStack stack, EntityPlayer playerIn, World worldIn, EnumHand handIn) {
//        if (worldIn.isRemote)
//            return new ActionResult(EnumActionResult.SUCCESS,stack);
        playerIn.openGui(MM.instance, FilterGui.GUI_ID,worldIn, playerIn.getPosition().getX(),playerIn.getPosition().getY(),playerIn.getPosition().getZ());
        return new ActionResult(EnumActionResult.SUCCESS,stack);
    }
}
