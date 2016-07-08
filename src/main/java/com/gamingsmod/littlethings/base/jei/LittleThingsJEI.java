package com.gamingsmod.littlethings.base.jei;

import com.gamingsmod.littlethings.base.loader.SectionLoader;
import com.gamingsmod.littlethings.decoration.addition.MetalFurnaces;
import com.gamingsmod.littlethings.decoration.addition.VanillaCraftingTables;
import com.gamingsmod.littlethings.decoration.container.ContainerCraftingTable;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

import static net.minecraftforge.oredict.OreDictionary.WILDCARD_VALUE;

@JEIPlugin
public class LittleThingsJEI extends BlankModPlugin
{
    @Override
    public void register(@Nonnull IModRegistry registry)
    {
        IRecipeTransferRegistry recipeTransferHandler = registry.getRecipeTransferRegistry();

        if (SectionLoader.isAdditionEnabled(VanillaCraftingTables.class))
            registry.addRecipeCategoryCraftingItem(new ItemStack(VanillaCraftingTables.crafting_table, 1, WILDCARD_VALUE), VanillaRecipeCategoryUid.CRAFTING);

        if (SectionLoader.isAdditionEnabled(MetalFurnaces.class)) {
            registry.addRecipeCategoryCraftingItem(new ItemStack(MetalFurnaces.iron_furnace), VanillaRecipeCategoryUid.SMELTING, VanillaRecipeCategoryUid.FUEL);
            registry.addRecipeCategoryCraftingItem(new ItemStack(MetalFurnaces.gold_furnace), VanillaRecipeCategoryUid.SMELTING, VanillaRecipeCategoryUid.FUEL);
            registry.addRecipeCategoryCraftingItem(new ItemStack(MetalFurnaces.diamond_furnace), VanillaRecipeCategoryUid.SMELTING, VanillaRecipeCategoryUid.FUEL);
            registry.addRecipeCategoryCraftingItem(new ItemStack(MetalFurnaces.emerald_furnace), VanillaRecipeCategoryUid.SMELTING, VanillaRecipeCategoryUid.FUEL);
        }

        recipeTransferHandler.addRecipeTransferHandler(ContainerCraftingTable.class, VanillaRecipeCategoryUid.CRAFTING, 1, 9, 10, 36);
    }
}
