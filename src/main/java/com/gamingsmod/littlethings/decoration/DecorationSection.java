package com.gamingsmod.littlethings.decoration;

import com.gamingsmod.littlethings.base.loader.Section;
import com.gamingsmod.littlethings.decoration.addition.MetalFurnaces;
import com.gamingsmod.littlethings.decoration.addition.MobChests;
import com.gamingsmod.littlethings.decoration.addition.VanillaCraftingTables;

public class DecorationSection extends Section
{
    @Override
    public void registerAdditions()
    {
        addAddition(new VanillaCraftingTables());
        addAddition(new MetalFurnaces());
        addAddition(new MobChests());
    }
}
