package com.gamingsmod.littlethings.base.loader;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Addition
{
    public String category;

    public void preInit(FMLPreInitializationEvent e)
    {

    }

    public void init(FMLInitializationEvent e)
    {

    }

    public void postInit(FMLPostInitializationEvent e)
    {

    }

    @SideOnly(Side.CLIENT)
    public void clientPreInit(FMLPreInitializationEvent e)
    {

    }

    @SideOnly(Side.CLIENT)
    public void clientInit(FMLInitializationEvent e)
    {

    }

    @SideOnly(Side.CLIENT)
    public void clientPostInit(FMLPostInitializationEvent e)
    {

    }

    public void serverStarting(FMLServerStartingEvent e)
    {

    }

    public void setupConfig()
    {

    }

    public boolean hasSubscriptions()
    {
        return false;
    }

    public boolean enabledByDefault()
    {
        return true;
    }

    public boolean isClient()
    {
        return FMLCommonHandler.instance().getSide().isClient();
    }
}
