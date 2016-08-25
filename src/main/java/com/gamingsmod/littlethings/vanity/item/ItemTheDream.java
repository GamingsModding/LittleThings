package com.gamingsmod.littlethings.vanity.item;

import com.gamingsmod.littlethings.base.item.ModItem;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockGravel;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

import java.util.List;

public class ItemTheDream extends ModItem
{
    public ItemTheDream()
    {
        setUnlocalizedName("the_dream");
        setCreativeTab(CreativeTabs.MISC);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (!playerIn.isSneaking() || hand == EnumHand.OFF_HAND)
            return EnumActionResult.FAIL;

        if (facing == EnumFacing.UP || facing == EnumFacing.DOWN)
            facing = EnumFacing.NORTH;

        if (facing.getAxis() == EnumFacing.Axis.Z) {
            for (int x = -15; x < 15; x++)
                for (int y = 0; y < 10; y++)
                    if (facing == EnumFacing.NORTH)
                        for (int z = 0; z < 15; z++)
                            safeToClearBlock(worldIn, pos.add(x, y, z));
                    else if (facing == EnumFacing.SOUTH)
                        for (int z = 0; z > -15; z--)
                            safeToClearBlock(worldIn, pos.add(x, y, z));

        } else if (facing.getAxis() == EnumFacing.Axis.X) {
            for (int z = -15; z < 15; z++)
                for (int y = 0; y < 10; y++)
                    if (facing == EnumFacing.EAST)
                        for (int x = 0; x > -15; x--)
                            safeToClearBlock(worldIn, pos.add(x, y, z));
                    else if (facing == EnumFacing.WEST)
                        for (int x = 0; x < 15; x++)
                            safeToClearBlock(worldIn, pos.add(x, y, z));
        }


        if (!playerIn.isCreative()) {
            stack.stackSize--;
            if (stack.stackSize <= 0)
                playerIn.setHeldItem(hand, null);
        }

        return EnumActionResult.PASS;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
        tooltip.add(I18n.translateToLocal("item.littlethings:the_dream.desc.1"));
        tooltip.add(I18n.translateToLocal("item.littlethings:the_dream.desc.2"));

        if (GuiScreen.isShiftKeyDown()) {
            tooltip.add(I18n.translateToLocal("item.littlethings:the_dream.desc.shift.1"));
            tooltip.add(I18n.translateToLocal("item.littlethings:the_dream.desc.shift.2"));
        }
    }

    public void safeToClearBlock(World worldIn, BlockPos pos) {
        IBlockState state = worldIn.getBlockState(pos);
        if (state.getBlock() == Blocks.NETHERRACK) worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
        else if (state.getBlock() instanceof BlockStone) worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
        else if (state.getBlock() instanceof BlockDirt) worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
        else if (state.getBlock() instanceof BlockGravel) worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
        else if (state.getBlock() instanceof BlockStaticLiquid)
            if (state.getBlock().getMaterial(state) == Material.LAVA)
                worldIn.setBlockState(pos, Blocks.OBSIDIAN.getDefaultState());
            else if (state.getBlock().getMaterial(state) == Material.WATER)
                worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
    }
}
