package com.gamingsmod.littlethings.automation.addition;

import com.gamingsmod.littlethings.base.loader.Addition;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RightClickCrops extends Addition
{
    @SubscribeEvent
    public void onPlayerRightClick(PlayerInteractEvent.RightClickBlock e)
    {
        World worldObj = e.getWorld();
        BlockPos pos = e.getPos();
        IBlockState block = worldObj.getBlockState(pos);

        if (block.getBlock() instanceof BlockCrops) {
            BlockCrops crop = (BlockCrops) block.getBlock();
            if (crop.isMaxAge(block)) {
                crop.dropBlockAsItemWithChance(worldObj, pos, block, 1, 0);
                worldObj.setBlockState(pos, crop.withAge(0));
            }
        }
    }

    @Override
    public boolean hasSubscriptions()
    {
        return true;
    }
}
