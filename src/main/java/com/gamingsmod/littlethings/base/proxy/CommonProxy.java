package com.gamingsmod.littlethings.base.proxy;

import com.gamingsmod.littlethings.base.LittleThings;
import com.gamingsmod.littlethings.base.loader.SectionLoader;
import com.gamingsmod.littlethings.base.network.GuiHandler;
import com.gamingsmod.littlethings.base.network.MessageHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent e)
    {
        SectionLoader.preInit(e);

        NetworkRegistry.INSTANCE.registerGuiHandler(LittleThings.instance, new GuiHandler());
        MessageHandler.init();
    }

    public void init(FMLInitializationEvent e)
    {
        SectionLoader.init(e);
    }

    public void postInit(FMLPostInitializationEvent e)
    {
        SectionLoader.postInit(e);
    }

    public void serverStarting(FMLServerStartingEvent e)
    {
        SectionLoader.serverStarting(e);
    }
}
