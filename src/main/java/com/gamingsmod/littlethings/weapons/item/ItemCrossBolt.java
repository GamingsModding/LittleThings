package com.gamingsmod.littlethings.weapons.item;

import com.gamingsmod.littlethings.base.item.ModItemVariants;
import net.minecraft.creativetab.CreativeTabs;

public class ItemCrossBolt extends ModItemVariants
{
    public ItemCrossBolt()
    {
        super("normal", "explosive", "spectral");
        setUnlocalizedName("cross_bolt");
        setCreativeTab(CreativeTabs.COMBAT);
    }
}
