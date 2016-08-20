package com.gamingsmod.littlethings.vanity;

import com.gamingsmod.littlethings.base.loader.Section;
import com.gamingsmod.littlethings.vanity.addition.ArmorStandGui;
import com.gamingsmod.littlethings.vanity.addition.NoConflictRecipes;
import com.gamingsmod.littlethings.vanity.addition.RawFoodHunger;

public class VanitySection extends Section
{
    @Override
    public void registerAdditions()
    {
        addAddition(new NoConflictRecipes());
        addAddition(new RawFoodHunger(), "Raw food gives hunger effect");
        addAddition(new ArmorStandGui());
    }
}
