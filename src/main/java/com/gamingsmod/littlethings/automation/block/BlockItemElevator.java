package com.gamingsmod.littlethings.automation.block;

import com.gamingsmod.littlethings.automation.tileentity.TileEntityItemElevator;
import com.gamingsmod.littlethings.base.LittleThings;
import com.gamingsmod.littlethings.base.block.ModBlockInventory;
import com.gamingsmod.littlethings.base.network.GuiHandler;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockItemElevator extends ModBlockInventory
{
    public BlockItemElevator()
    {
        super(Material.ROCK);
        setUnlocalizedName("item_elevator");
        setHardness(3.5F);
        setSoundType(SoundType.STONE);
        setCreativeTab(CreativeTabs.REDSTONE);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityItemElevator();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (!worldIn.isRemote)
            playerIn.openGui(LittleThings.instance, GuiHandler.ITEM_ELEVATOR, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    public boolean hasComparatorInputOverride(IBlockState state)
    {
        return true;
    }

    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos)
    {
        return Container.calcRedstone(worldIn.getTileEntity(pos));
    }
}
