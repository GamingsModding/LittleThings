package com.gamingsmod.littlethings.weapons;

import com.gamingsmod.littlethings.base.loader.Section;
import com.gamingsmod.littlethings.weapons.addition.Crossbow;

public class WeaponsSection extends Section
{
    @Override
    public void registerAdditions()
    {
        addAddition(new Crossbow());
    }
}
