package com.gamingsmod.littlethings.automation;

import com.gamingsmod.littlethings.automation.addition.ItemElevator;
import com.gamingsmod.littlethings.automation.addition.RedstoneClock;
import com.gamingsmod.littlethings.automation.addition.RightClickCrops;
import com.gamingsmod.littlethings.base.loader.Section;

public class AutomationSection extends Section
{
    @Override
    public void registerAdditions()
    {
        addAddition(new ItemElevator());
        addAddition(new RightClickCrops());
        addAddition(new RedstoneClock());
    }
}
