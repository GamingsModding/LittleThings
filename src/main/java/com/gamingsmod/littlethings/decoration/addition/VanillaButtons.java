package com.gamingsmod.littlethings.decoration.addition;

import com.gamingsmod.littlethings.base.block.ModBlock;
import com.gamingsmod.littlethings.base.helper.RecipeHelper;
import com.gamingsmod.littlethings.base.loader.Addition;
import com.gamingsmod.littlethings.decoration.block.BlockVanillaButtons;
import com.gamingsmod.littlethings.decoration.block.BlockVanillaCraftingTable;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class VanillaButtons extends Addition
{
    public static ModBlock[] buttons = new ModBlock[BlockVanillaCraftingTable.Variants.values().length];

    @Override
    public void preInit(FMLPreInitializationEvent e)
    {
        int i = 0;
        for (BlockVanillaCraftingTable.Variants v : BlockVanillaCraftingTable.Variants.values()) {
            buttons[i++] = new BlockVanillaButtons(v.getName());
        }
    }

    @Override
    public void init(FMLInitializationEvent e)
    {
        for (int i = 0; i < BlockVanillaCraftingTable.Variants.values().length; i++)
            RecipeHelper.addShapelessRecipe(new ItemStack(buttons[i]), new ItemStack(Blocks.PLANKS, 1, i + 1));
    }
}
