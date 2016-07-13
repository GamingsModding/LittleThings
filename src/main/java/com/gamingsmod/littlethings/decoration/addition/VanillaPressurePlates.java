package com.gamingsmod.littlethings.decoration.addition;

import com.gamingsmod.littlethings.base.block.ModBlock;
import com.gamingsmod.littlethings.base.helper.RecipeHelper;
import com.gamingsmod.littlethings.base.loader.Addition;
import com.gamingsmod.littlethings.decoration.block.BlockVanillaPressurePlates;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class VanillaPressurePlates extends Addition
{
    public static ModBlock pressure_plates;

    @Override
    public void preInit(FMLPreInitializationEvent e)
    {
        pressure_plates = new BlockVanillaPressurePlates();
    }

    @Override
    public void init(FMLInitializationEvent e)
    {
        for (int i = 0; i < pressure_plates.variants.length; i++)
            RecipeHelper.addShapedRecipe(new ItemStack(pressure_plates, 1, i),
                    "ww",
                    'w', new ItemStack(Blocks.PLANKS, 1, i + 1));
    }
}
