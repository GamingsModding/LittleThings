package com.gamingsmod.littlethings.command;

import com.gamingsmod.littlethings.base.loader.Section;
import com.gamingsmod.littlethings.command.addition.GamemodeShortcuts;
import com.gamingsmod.littlethings.command.addition.TeleportDimension;

public class CommandSection extends Section
{
    @Override
    public void registerAdditions()
    {
        addAddition(new GamemodeShortcuts());
        addAddition(new TeleportDimension());
    }
}
