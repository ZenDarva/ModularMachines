package com.gmail.zendarva.mm.blocks;


import com.gmail.zendarva.mm.MM;
import com.gmail.zendarva.mm.entities.MachineFrameEntity;
import com.gmail.zendarva.mm.items.MachineFrameItem;
import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;


public class MachineFrameBlock extends Block implements ITileEntityProvider {

    public static final int GUI_ID = 1;

    public static final PropertyBool stateProperty= PropertyBool.create("running");

    public static final PropertyDirection FACING = PropertyDirection.create("facing", new Predicate<EnumFacing>()
    {
        public boolean apply(@Nullable EnumFacing p_apply_1_)
        {
            return p_apply_1_ != EnumFacing.UP && p_apply_1_ != EnumFacing.DOWN;
        }
    });


    public MachineFrameBlock(Material materialIn) {
        super(Material.ANVIL);
        setUnlocalizedName(MM.MODID + ".machineframe");
        setRegistryName("machineframe");
        GameRegistry.register(this);

        GameRegistry.register(new MachineFrameItem(this),getRegistryName());
        GameRegistry.registerTileEntity(MachineFrameEntity.class, this.getUnlocalizedName());
        setDefaultState(blockState.getBaseState().withProperty(stateProperty, false).withProperty(FACING,EnumFacing.EAST));
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new MachineFrameEntity();
    }

    public static EnumFacing getFacing(int meta)
    {
        return EnumFacing.getFront(meta & 7);
    }
    public static boolean isEnabled(int meta)
    {
        return (meta & 8) != 8;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, getFacing(meta)).withProperty(stateProperty, Boolean.valueOf(isEnabled(meta)));
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING,placer.getHorizontalFacing().getOpposite()).withProperty(stateProperty,false);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        i = i | ((EnumFacing)state.getValue(FACING)).getIndex();

        if (!((Boolean)state.getValue(stateProperty)).booleanValue())
        {
            i |= 8;
        }
        return i;
    }


    @Override
    public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis) {
        return super.rotateBlock(world, pos, axis);
    }


    @SideOnly(Side.CLIENT)
    public void initModel() {

        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {FACING, stateProperty});
    }



    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        updateTick(worldIn, pos, state, null);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (world.isRemote)
            return;
        int powered = world.isBlockIndirectlyGettingPowered(pos);
        if (powered > 1)
            world.setBlockState(pos, state.withProperty(stateProperty, false),2);
        else
            world.setBlockState(pos, state.withProperty(stateProperty, true),2);

    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote)
            return true;
        TileEntity tileEntity= worldIn.getTileEntity(pos);
        if (!(tileEntity instanceof MachineFrameEntity))
            return false;
        playerIn.openGui(MM.instance,GUI_ID,worldIn,pos.getX(),pos.getY(),pos.getZ());
        return true;
    }
}
