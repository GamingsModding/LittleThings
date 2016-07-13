package com.gamingsmod.littlethings.decoration.block;

import com.gamingsmod.littlethings.base.block.ModBlockHorizontal;
import com.gamingsmod.littlethings.base.helper.NBTHelper;
import com.gamingsmod.littlethings.base.lib.LibMisc;
import com.gamingsmod.littlethings.base.loader.SectionLoader;
import com.gamingsmod.littlethings.base.tileentity.ModTileInventory;
import com.gamingsmod.littlethings.decoration.addition.MobChests;
import com.gamingsmod.littlethings.decoration.tileentity.TileEntityMobChest;
import com.gamingsmod.littlethings.decoration.tileentity.render.TileEntityMobChestRender;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;

public class BlockMobChests extends ModBlockHorizontal implements ITileEntityProvider
{
    public static final String NBT_MOB_TAG = "mob";
    protected static final AxisAlignedBB CHEST_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.875D, 0.9375D);
    private String mob_temp;

    public BlockMobChests()
    {
        super(Material.ROCK);
        setHardness(2.5F);
        setSoundType(SoundType.WOOD);

        setRegistryName(LibMisc.PREFIX_MOD + "mob_chest_block");
        SectionLoader.blocks.add(this);
        GameRegistry.register(this);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return CHEST_AABB;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        List<ItemStack> drops = new ArrayList<>();

        ItemStack dropping = new ItemStack(MobChests.mob_chest_item, 1, 0);
        NBTHelper.setString(dropping, NBT_MOB_TAG, mob_temp);
        drops.add(dropping);

        return drops;
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        ItemStack stack = new ItemStack(MobChests.mob_chest_item, 1, 0);
        TileEntityMobChest te = (TileEntityMobChest) world.getTileEntity(pos);
        NBTHelper.setString(stack, NBT_MOB_TAG, te.getMob());
        return stack;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityMobChest();
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        TileEntity te = world.getTileEntity(pos);

        if (te instanceof TileEntityMobChest) ((TileEntityMobChest) te).setMob(NBTHelper.getString(stack, NBT_MOB_TAG));

        if (stack.hasDisplayName()) ((ModTileInventory) world.getTileEntity(pos)).setCustomName(stack.getDisplayName());
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (!worldIn.isRemote) playerIn.displayGUIChest((TileEntityMobChest) worldIn.getTileEntity(pos));
        return true;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntity te = worldIn.getTileEntity(pos);

        if (te instanceof TileEntityMobChest)
            mob_temp = ((TileEntityMobChest) te).getMob();

        if (te instanceof IInventory) {
            InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory) te);
            worldIn.updateComparatorOutputLevel(pos, this);
        }

        super.breakBlock(worldIn, pos, state);
        worldIn.removeTileEntity(pos);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param)
    {
        super.eventReceived(state, worldIn, pos, id, param);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity != null && tileentity.receiveClientEvent(id, param);
    }

    @Override
    public void registerRender()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMobChest.class, new TileEntityMobChestRender());
    }
}
