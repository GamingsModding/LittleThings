package com.gamingsmod.littlethings.command;

import com.gamingsmod.littlethings.base.loader.Section;
import com.gamingsmod.littlethings.command.addition.GamemodeShortcuts;
import com.gamingsmod.littlethings.command.addition.TeleportDimension;
import com.gamingsmod.littlethings.command.addition.TimeShortcuts;

public class CommandSection extends Section
{
    @Override
    public void registerAdditions()
    {
        addAddition(new GamemodeShortcuts());
        addAddition(new TeleportDimension());
        addAddition(new TimeShortcuts());
    }
}
