package com.gamingsmod.littlethings.vanity.addition;

import com.gamingsmod.littlethings.base.helper.RecipeHelper;
import com.gamingsmod.littlethings.base.item.ModItem;
import com.gamingsmod.littlethings.base.loader.Addition;
import com.gamingsmod.littlethings.vanity.item.ItemTheDream;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class TheDream extends Addition
{
    public static ModItem the_dream;

    @Override
    public void preInit(FMLPreInitializationEvent e)
    {
        the_dream = new ItemTheDream();
    }

    @Override
    public void init(FMLInitializationEvent e)
    {
        RecipeHelper.addShapedRecipe(the_dream,
                " p ", "prp", " p ",
                'r', new ItemStack(Blocks.REDSTONE_BLOCK),
                'p', new ItemStack(Items.IRON_PICKAXE));
    }
}
