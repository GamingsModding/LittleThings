package com.gamingsmod.littlethings.decoration.addition;

import com.gamingsmod.littlethings.base.block.ModBlock;
import com.gamingsmod.littlethings.base.helper.RecipeHelper;
import com.gamingsmod.littlethings.base.lib.LibMisc;
import com.gamingsmod.littlethings.base.loader.Addition;
import com.gamingsmod.littlethings.decoration.block.BlockMetalFurnace;
import com.gamingsmod.littlethings.decoration.tileentity.TileEntityMetalFurnace;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static com.gamingsmod.littlethings.base.helper.ConfigHelper.getInt;

public class MetalFurnaces extends Addition
{
    public static int iron_furnace_burn;
    public static int gold_furnace_burn;
    public static int diamond_furnace_burn;
    public static int emerlad_furnace_burn;

    public static ModBlock iron_furnace;
    public static ModBlock gold_furnace;
    public static ModBlock diamond_furnace;
    public static ModBlock emerald_furnace;

    @Override
    public void preInit(FMLPreInitializationEvent e)
    {
        iron_furnace = new BlockMetalFurnace("iron", iron_furnace_burn);
        gold_furnace = new BlockMetalFurnace("gold", gold_furnace_burn);
        diamond_furnace = new BlockMetalFurnace("diamond", diamond_furnace_burn);
        emerald_furnace = new BlockMetalFurnace("emerald", emerlad_furnace_burn);

        GameRegistry.registerTileEntity(TileEntityMetalFurnace.class, LibMisc.PREFIX_MOD + "metal_furnace");
    }

    @Override
    public void setupConfig()
    {
        iron_furnace_burn = getInt(category, "Iron Furnace Burn Time", 150, 10, 200, "Length of ticks it takes to smelt one item (Normal Furnace: 200)");
        gold_furnace_burn = getInt(category, "Gold Furnace Burn Time", 100, 10, 200, "Length of ticks it takes to smelt one item (Normal Furnace: 200)");
        diamond_furnace_burn = getInt(category, "Diamond Furnace Burn Time", 50, 10, 200, "Length of ticks it takes to smelt one item (Normal Furnace: 200)");
        emerlad_furnace_burn = getInt(category, "Emerald Furnace Burn Time", 50, 10, 200, "Length of ticks it takes to smelt one item (Normal Furnace: 200)");
    }

    @Override
    public void init(FMLInitializationEvent e)
    {
        RecipeHelper.addShapedRecipe(iron_furnace,
                "iii", "ifi", "iii",
                'i', "ingotIron",
                'f', new ItemStack(Blocks.FURNACE));

        RecipeHelper.addShapedRecipe(gold_furnace,
                "iii", "ifi", "iii",
                'i', "ingotGold",
                'f', new ItemStack(iron_furnace));

        RecipeHelper.addShapedRecipe(diamond_furnace,
                "iii", "ifi", "iii",
                'i', "gemDiamond",
                'f', new ItemStack(gold_furnace));

        RecipeHelper.addShapedRecipe(emerald_furnace,
                "iii", "ifi", "iii",
                'i', "gemEmerald",
                'f', new ItemStack(gold_furnace));
    }
}
