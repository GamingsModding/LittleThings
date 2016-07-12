package com.gamingsmod.littlethings.command.addition;

import com.gamingsmod.littlethings.base.loader.Addition;
import com.gamingsmod.littlethings.command.command.CommandTeleportDim;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class TeleportDimension extends Addition
{
    @Override
    public void serverStarting(FMLServerStartingEvent e)
    {
        e.registerServerCommand(new CommandTeleportDim());
    }
}
