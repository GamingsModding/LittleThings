package com.gamingsmod.littlethings.automation.addition;

import com.gamingsmod.littlethings.automation.block.BlockRedstoneClock;
import com.gamingsmod.littlethings.automation.tileentity.TileEntityRedstoneClock;
import com.gamingsmod.littlethings.base.block.ModBlock;
import com.gamingsmod.littlethings.base.helper.RecipeHelper;
import com.gamingsmod.littlethings.base.lib.LibMisc;
import com.gamingsmod.littlethings.base.loader.Addition;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RedstoneClock extends Addition
{
    public static ModBlock redstone_clock;

    @Override
    public void preInit(FMLPreInitializationEvent e)
    {
        redstone_clock = new BlockRedstoneClock();

        GameRegistry.registerTileEntity(TileEntityRedstoneClock.class, LibMisc.PREFIX_MOD + "redstone_clock");
    }

    @Override
    public void init(FMLInitializationEvent e)
    {
        RecipeHelper.addShapelessRecipe(redstone_clock,
                new ItemStack(Blocks.REDSTONE_BLOCK),
                new ItemStack(Items.CLOCK));
    }
}
