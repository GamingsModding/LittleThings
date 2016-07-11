package com.gamingsmod.littlethings.decoration.item;

import com.gamingsmod.littlethings.base.helper.NBTHelper;
import com.gamingsmod.littlethings.base.item.ModItem;
import com.gamingsmod.littlethings.base.lib.LibMisc;
import com.gamingsmod.littlethings.decoration.addition.MobChests;
import com.gamingsmod.littlethings.decoration.block.BlockMobChests;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class ItemMobChest extends ModItem
{
    public ItemMobChest()
    {
        setUnlocalizedName("mob_chest");
        setCreativeTab(CreativeTabs.DECORATIONS);
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems)
    {
        for (String mob : MobChests.OPEN_SOUND.keySet()) {
            ItemStack stack = new ItemStack(itemIn, 1, 0);
            NBTHelper.setString(stack, BlockMobChests.NBT_MOB_TAG, mob);
            subItems.add(stack);
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack)
    {
        return super.getItemStackDisplayName(stack) + " (" +
                I18n.format("entity." +
                        StringUtils.capitalize(NBTHelper.getString(stack, BlockMobChests.NBT_MOB_TAG))
                        + ".name") +
                ")";
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        Block block = iblockstate.getBlock();

        if (!block.isReplaceable(worldIn, pos)) pos = pos.offset(facing);

        if (stack.stackSize != 0 && playerIn.canPlayerEdit(pos, facing, stack) && worldIn.canBlockBePlaced(MobChests.mob_chest, pos, false, facing, null, stack)) {
            int i = this.getMetadata(stack.getMetadata());
            IBlockState iblockstate1 = MobChests.mob_chest.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, i, playerIn);

            if (placeBlockAt(stack, playerIn, worldIn, pos, facing, hitX, hitY, hitZ, iblockstate1)) {
                SoundType soundtype = MobChests.mob_chest.getSoundType();
                worldIn.playSound(playerIn, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                --stack.stackSize;
            }

            return EnumActionResult.SUCCESS;
        } else {
            return EnumActionResult.FAIL;
        }
    }

    @Override
    public void registerItemVariants()
    {
        for (String mob : MobChests.OPEN_SOUND.keySet())
            ModelBakery.registerItemVariants(this, new ResourceLocation(LibMisc.PREFIX_MOD + baseName + "_" + mob));
    }

    @Override
    public ItemMeshDefinition registerCustomMeshDefinition()
    {
        return new ItemMeshDefinition()
        {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack)
            {
                String mob = NBTHelper.getString(stack, BlockMobChests.NBT_MOB_TAG);

                if (mob.equals("")) mob = "cow";

                return new ModelResourceLocation(LibMisc.PREFIX_MOD + stack.getUnlocalizedName().substring(6 + LibMisc.MOD_ID.length()) + "_" + mob, "inventory");
            }
        };
    }

    private boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState)
    {
        if (!world.setBlockState(pos, newState, 3)) return false;

        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() == MobChests.mob_chest) {
            ItemBlock.setTileEntityNBT(world, player, pos, stack);
            MobChests.mob_chest.onBlockPlacedBy(world, pos, state, player, stack);
        }

        return true;
    }
}
