package com.gamingsmod.littlethings.world;

import com.gamingsmod.littlethings.base.loader.Section;
import com.gamingsmod.littlethings.world.addition.EmeraldsEverywhere;

public class WorldSection extends Section
{
    @Override
    public void registerAdditions()
    {
        addAddition(new EmeraldsEverywhere());
    }
}
