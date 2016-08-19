package com.gamingsmod.littlethings.command.addition;

import com.gamingsmod.littlethings.base.loader.Addition;
import com.gamingsmod.littlethings.command.command.CommandTimeShortcut;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class TimeShortcuts extends Addition
{
    @Override
    public void serverStarting(FMLServerStartingEvent e)
    {
        e.registerServerCommand(new CommandTimeShortcut(CommandTimeShortcut.DAY));
        e.registerServerCommand(new CommandTimeShortcut(CommandTimeShortcut.NIGHT));
    }
}
