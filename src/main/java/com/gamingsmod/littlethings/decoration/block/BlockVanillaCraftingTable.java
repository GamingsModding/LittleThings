package com.gamingsmod.littlethings.decoration.block;

import com.gamingsmod.littlethings.base.block.ModBlockVariants;
import com.gamingsmod.littlethings.decoration.container.ContainerCraftingTable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockVanillaCraftingTable extends ModBlockVariants
{
    public BlockVanillaCraftingTable()
    {
        super(Material.WOOD, Variants.class);
        setCreativeTab(CreativeTabs.DECORATIONS);
        setUnlocalizedName("crafting_table");
        setHardness(2.5F);
        setSoundType(SoundType.WOOD);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (!worldIn.isRemote) {
            playerIn.displayGui(new InterfaceCraftingTable(worldIn, pos));
            playerIn.addStat(StatList.CRAFTING_TABLE_INTERACTION);
        }
        return true;
    }

    @Override
    public String getSpecialName(ItemStack stack)
    {
        return Variants.values()[stack.getMetadata()].getName();
    }

    public enum Variants implements EnumBase
    {
        SPRUCE,
        BIRCH,
        JUNGLE,
        ACACIA,
        DARK_OAK
    }

    public static class InterfaceCraftingTable implements IInteractionObject
    {
        private final World world;
        private final BlockPos position;

        public InterfaceCraftingTable(World worldIn, BlockPos pos)
        {
            this.world = worldIn;
            this.position = pos;
        }

        @Override
        public String getName()
        {
            return null;
        }

        @Override
        public boolean hasCustomName()
        {
            return false;
        }

        @Override
        public ITextComponent getDisplayName()
        {
            return new TextComponentTranslation(Blocks.CRAFTING_TABLE.getUnlocalizedName() + ".name");
        }

        @Override
        public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
        {
            return new ContainerCraftingTable(playerInventory, this.world, this.position);
        }

        @Override
        public String getGuiID()
        {
            return "minecraft:crafting_table";
        }
    }
}
