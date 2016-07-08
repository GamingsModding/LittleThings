package com.gamingsmod.littlethings.building;

import com.gamingsmod.littlethings.base.loader.Section;
import com.gamingsmod.littlethings.building.addition.ClearGlass;

public class BuildingSection extends Section
{
    @Override
    public void registerAdditions()
    {
        addAddition(new ClearGlass());
    }
}
