package com.gamingsmod.littlethings.base.helper;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class RecipeHelper
{
    public static void addShapedRecipe(Block output, Object... recipe)
    {
        addShapedRecipe(new ItemStack(output), recipe);
    }

    public static void addShapedRecipe(Item output, Object... recipe)
    {
        addShapedRecipe(new ItemStack(output), recipe);
    }

    public static void addShapedRecipe(ItemStack output, Object... recipe)
    {
        GameRegistry.addRecipe(new ShapedOreRecipe(output, recipe));
    }

    public static void addShapelessRecipe(Block output, Object... recipe)
    {
        addShapelessRecipe(new ItemStack(output), recipe);
    }

    public static void addShapelessRecipe(Item output, Object... recipe)
    {
        addShapelessRecipe(new ItemStack(output), recipe);
    }

    public static void addShapelessRecipe(ItemStack output, Object... recipe)
    {
        GameRegistry.addRecipe(new ShapelessOreRecipe(output, recipe));
    }

    public static void removeFirstRecipeFor(Block block)
    {
        removeFirstRecipeFor(Item.getItemFromBlock(block));
    }

    public static void removeFirstRecipeFor(Item item)
    {
        for (Object recipe : CraftingManager.getInstance().getRecipeList()) {
            if (recipe != null) {
                ItemStack stack = ((IRecipe) recipe).getRecipeOutput();
                if (stack != null && stack.getItem() == item) {
                    CraftingManager.getInstance().getRecipeList().remove(recipe);
                    return;
                }
            }
        }
    }

    public static void addSmeltingRecipe(ItemStack output, ItemStack input)
    {
        addSmeltingRecipe(output, input, 0.3F);
    }

    public static void addSmeltingRecipe(ItemStack output, ItemStack input, float xp)
    {
        GameRegistry.addSmelting(input, output, xp);
    }
}
