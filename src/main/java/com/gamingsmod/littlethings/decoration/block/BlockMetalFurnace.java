package com.gamingsmod.littlethings.decoration.block;

import com.gamingsmod.littlethings.base.block.ModBlockHorizontal;
import com.gamingsmod.littlethings.decoration.tileentity.TileEntityMetalFurnace;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockMetalFurnace extends ModBlockHorizontal implements ITileEntityProvider
{
    private static final PropertyBool BURNING = PropertyBool.create("burning");
    private static boolean keepInventory;
    private int burnTime;

    public BlockMetalFurnace(String name, int burnTime)
    {
        super(Material.IRON);
        this.isBlockContainer = true;
        this.burnTime = burnTime;

        setUnlocalizedName("metal_furnace_" + name);
        setHardness(3.5F);
        setSoundType(SoundType.STONE);
        setDefaultState(getDefaultState().withProperty(FACING, EnumFacing.NORTH).withProperty(BURNING, false));

        setCreativeTab(CreativeTabs.DECORATIONS);

    }

    public static void setState(boolean active, World worldIn, BlockPos pos)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        keepInventory = true;

        if (active) {
            worldIn.setBlockState(pos, iblockstate.withProperty(FACING, iblockstate.getValue(FACING)).withProperty(BURNING, true), 3);
            worldIn.setBlockState(pos, iblockstate.withProperty(FACING, iblockstate.getValue(FACING)).withProperty(BURNING, true), 3);
//            worldIn.setBlockState(pos, Blocks.LIT_FURNACE.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
//            worldIn.setBlockState(pos, Blocks.LIT_FURNACE.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        } else {
            worldIn.setBlockState(pos, iblockstate.withProperty(FACING, iblockstate.getValue(FACING)).withProperty(BURNING, false), 3);
            worldIn.setBlockState(pos, iblockstate.withProperty(FACING, iblockstate.getValue(FACING)).withProperty(BURNING, false), 3);
//            worldIn.setBlockState(pos, Blocks.FURNACE.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
//            worldIn.setBlockState(pos, Blocks.FURNACE.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        }

        keepInventory = false;

        if (tileentity != null) {
            tileentity.validate();
            worldIn.setTileEntity(pos, tileentity);
        }
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return (int) (state.getValue(BURNING) ? 0.875F * 15.0 : 0);
    }

    @Override
    @Nullable
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(Blocks.FURNACE);
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("incomplete-switch")
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        if (stateIn.getValue(BURNING)) {
            EnumFacing enumfacing = stateIn.getValue(FACING);
            double d0 = (double) pos.getX() + 0.5D;
            double d1 = (double) pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
            double d2 = (double) pos.getZ() + 0.5D;
            double d3 = 0.52D;
            double d4 = rand.nextDouble() * 0.6D - 0.3D;

            if (rand.nextDouble() < 0.1D) {
                worldIn.playSound((double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }

            switch (enumfacing) {
                case WEST:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                    break;
                case EAST:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                    break;
                case NORTH:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D);
                    break;
                case SOUTH:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (!worldIn.isRemote) {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityMetalFurnace) {
                playerIn.displayGUIChest((TileEntityMetalFurnace) tileentity);
                playerIn.addStat(StatList.FURNACE_INTERACTION);
            }
        }

        return true;
    }

    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);

        if (stack.hasDisplayName()) {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityMetalFurnace) {
                ((TileEntityMetalFurnace) tileentity).setCustomInventoryName(stack.getDisplayName());
            }
        }
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState state)
    {
        return true;
    }

    @Override
    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos)
    {
        return Container.calcRedstone(worldIn.getTileEntity(pos));
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(this);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!keepInventory) {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityMetalFurnace) {
                InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityMetalFurnace) tileentity);
                worldIn.updateComparatorOutputLevel(pos, this);
            }
        }
        super.breakBlock(worldIn, pos, state);
        worldIn.removeTileEntity(pos);
    }

    @Override
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

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityMetalFurnace(burnTime);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        boolean burning = false;

        if ((meta & 8) > 0)
            burning = true;

        EnumFacing enumfacing = EnumFacing.getFront(meta - (burning ? 8 : 0));

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
            enumfacing = EnumFacing.NORTH;

        return getDefaultState().withProperty(FACING, enumfacing).withProperty(BURNING, burning);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getIndex() + (state.getValue(BURNING) ? 8 : 0);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, BURNING);
    }
}

