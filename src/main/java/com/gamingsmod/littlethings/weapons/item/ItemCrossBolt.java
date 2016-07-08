package com.gamingsmod.littlethings.weapons.item;

import com.gamingsmod.littlethings.base.item.ModItemVariants;

public class ItemCrossBolt extends ModItemVariants
{
    public ItemCrossBolt()
    {
        super(new String[]{"normal", "explosive", "spectral", "wither", "poison", "slowness"});
        setUnlocalizedName("cross_bolt");
    }
}
