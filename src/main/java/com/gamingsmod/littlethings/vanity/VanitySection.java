package com.gamingsmod.littlethings.vanity;

import com.gamingsmod.littlethings.base.loader.Section;
import com.gamingsmod.littlethings.vanity.addition.NoConflictRecipes;

public class VanitySection extends Section
{
    @Override
    public void registerAdditions()
    {
        addAddition(new NoConflictRecipes());
    }
}
