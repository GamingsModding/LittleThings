package com.gamingsmod.littlethings.command.addition;

import com.gamingsmod.littlethings.base.loader.Addition;
import com.gamingsmod.littlethings.command.command.CommandGamemodeShortcut;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class GamemodeShortcuts extends Addition
{
    @Override
    public void serverStarting(FMLServerStartingEvent e)
    {
        e.registerServerCommand(new CommandGamemodeShortcut("gmc", "creative"));
        e.registerServerCommand(new CommandGamemodeShortcut("gms", "survival"));
        e.registerServerCommand(new CommandGamemodeShortcut("gma", "adventure"));
        e.registerServerCommand(new CommandGamemodeShortcut("gmsp", "spectator"));
    }
}
