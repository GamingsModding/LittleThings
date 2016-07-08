package com.gamingsmod.littlethings.base.proxy;

import com.gamingsmod.littlethings.base.loader.SectionLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy
{
    @Override
    public void preInit(FMLPreInitializationEvent e)
    {
        super.preInit(e);
        SectionLoader.clientPreInit(e);
    }

    @Override
    public void init(FMLInitializationEvent e)
    {
        super.init(e);
        SectionLoader.clientInit(e);
    }

    @Override
    public void postInit(FMLPostInitializationEvent e)
    {
        super.postInit(e);
        SectionLoader.clientPostInit(e);
    }
}
