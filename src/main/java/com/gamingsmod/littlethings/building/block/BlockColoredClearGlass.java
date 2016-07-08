package com.gamingsmod.littlethings.building.block;

import com.gamingsmod.littlethings.base.block.ModBlockColored;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockColoredClearGlass extends ModBlockColored
{
    private static final PropertyBool UP = PropertyBool.create("up");
    private static final PropertyBool DOWN = PropertyBool.create("down");
    private static final PropertyBool NORTH = PropertyBool.create("north");
    private static final PropertyBool SOUTH = PropertyBool.create("south");
    private static final PropertyBool EAST = PropertyBool.create("east");
    private static final PropertyBool WEST = PropertyBool.create("west");

    public BlockColoredClearGlass()
    {
        super(Material.GLASS);
        setUnlocalizedName("colored_clear_glass");
        setHardness(0.3F);
        setSoundType(SoundType.GLASS);
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        setDefaultState(blockState
                .getBaseState()
                .withProperty(COLOR, EnumDyeColor.WHITE)
                .withProperty(UP, false)
                .withProperty(DOWN, false)
                .withProperty(NORTH, false)
                .withProperty(SOUTH, false)
                .withProperty(EAST, false)
                .withProperty(WEST, false));
    }


    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        IBlockState origState = state;
        state = state.withProperty(UP, worldIn.getBlockState(pos.up()).equals(origState));
        state = state.withProperty(DOWN, worldIn.getBlockState(pos.down()).equals(origState));
        state = state.withProperty(NORTH, worldIn.getBlockState(pos.north()).equals(origState));
        state = state.withProperty(SOUTH, worldIn.getBlockState(pos.south()).equals(origState));
        state = state.withProperty(WEST, worldIn.getBlockState(pos.west()).equals(origState));
        state = state.withProperty(EAST, worldIn.getBlockState(pos.east()).equals(origState));
        return state;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, COLOR, UP, DOWN, NORTH, SOUTH, EAST, WEST);
    }
}
