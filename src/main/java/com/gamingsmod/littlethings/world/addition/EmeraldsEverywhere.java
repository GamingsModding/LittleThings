package com.gamingsmod.littlethings.world.addition;

import com.gamingsmod.littlethings.base.helper.ConfigHelper;
import com.gamingsmod.littlethings.base.loader.Addition;
import com.gamingsmod.littlethings.world.generator.EmeraldsGenerator;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class EmeraldsEverywhere extends Addition
{
    private int clusterSize;

    @Override
    public void preInit(FMLPreInitializationEvent e)
    {
        GameRegistry.registerWorldGenerator(new EmeraldsGenerator(clusterSize), 1);
    }

    @Override
    public void setupConfig()
    {
        clusterSize = ConfigHelper.getInt(category, "Cluster Size", 3, 1, 5);
    }
}
