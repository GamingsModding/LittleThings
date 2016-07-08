package com.gamingsmod.littlethings.decoration.addition;

import com.gamingsmod.littlethings.base.block.ModBlock;
import com.gamingsmod.littlethings.base.helper.RecipeHelper;
import com.gamingsmod.littlethings.base.loader.Addition;
import com.gamingsmod.littlethings.decoration.block.BlockVanillaCraftingTable;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;

import static net.minecraftforge.oredict.OreDictionary.WILDCARD_VALUE;

public class VanillaCraftingTables extends Addition
{
    public static ModBlock crafting_table;

    @Override
    public void preInit(FMLPreInitializationEvent e)
    {
        crafting_table = new BlockVanillaCraftingTable();
    }

    @Override
    public void init(FMLInitializationEvent e)
    {
        for (int i = 0; i < crafting_table.variants.length; i++)
            RecipeHelper.addShapedRecipe(new ItemStack(crafting_table, 1, i),
                    "ww", "ww",
                    'w', new ItemStack(Blocks.PLANKS, 1, i + 1));

        RecipeHelper.addShapelessRecipe(Blocks.CRAFTING_TABLE, new ItemStack(crafting_table, 1, WILDCARD_VALUE));

        OreDictionary.registerOre("workbench", new ItemStack(crafting_table, 1, WILDCARD_VALUE));
    }
}
