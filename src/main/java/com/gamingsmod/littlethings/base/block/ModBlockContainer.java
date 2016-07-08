package com.gamingsmod.littlethings.base.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class ModBlockContainer extends ModBlock implements ITileEntityProvider
{
    protected ModBlockContainer(Material material)
    {
        this(material, material.getMaterialMapColor());
    }

    protected ModBlockContainer(Material materialIn, MapColor color)
    {
        super(materialIn, color);
        this.isBlockContainer = true;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        super.breakBlock(worldIn, pos, state);
        worldIn.removeTileEntity(pos);
    }

    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param)
    {
        super.eventReceived(state, worldIn, pos, id, param);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity != null && tileentity.receiveClientEvent(id, param);
    }
}
