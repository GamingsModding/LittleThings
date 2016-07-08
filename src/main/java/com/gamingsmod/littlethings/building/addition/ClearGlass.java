package com.gamingsmod.littlethings.building.addition;

import com.gamingsmod.littlethings.base.block.ModBlock;
import com.gamingsmod.littlethings.base.block.ModBlockColored;
import com.gamingsmod.littlethings.base.loader.Addition;
import com.gamingsmod.littlethings.building.block.BlockClearGlass;
import com.gamingsmod.littlethings.building.block.BlockColoredClearGlass;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClearGlass extends Addition
{
    public static ModBlock clear_glass;
    public static ModBlockColored colored_clear_glass;

    @Override
    public void preInit(FMLPreInitializationEvent e)
    {
        clear_glass = new BlockClearGlass();
        colored_clear_glass = new BlockColoredClearGlass();
    }
}
