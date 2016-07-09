package com.gamingsmod.littlethings.building.addition;

import com.gamingsmod.littlethings.base.block.ModBlock;
import com.gamingsmod.littlethings.base.block.ModBlockColored;
import com.gamingsmod.littlethings.base.helper.ConfigHelper;
import com.gamingsmod.littlethings.base.helper.RecipeHelper;
import com.gamingsmod.littlethings.base.loader.Addition;
import com.gamingsmod.littlethings.building.block.BlockClearGlass;
import com.gamingsmod.littlethings.building.block.BlockColoredClearGlass;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClearGlass extends Addition
{
    public static ModBlock clear_glass;
    public static ModBlockColored colored_clear_glass;

    private boolean enable_colored_clear_glass;

    @Override
    public void preInit(FMLPreInitializationEvent e)
    {
        clear_glass = new BlockClearGlass();

        if (enable_colored_clear_glass)
            colored_clear_glass = new BlockColoredClearGlass();
    }

    @Override
    public void init(FMLInitializationEvent e)
    {
        RecipeHelper.addSmeltingRecipe(new ItemStack(clear_glass), new ItemStack(Blocks.GLASS));
        for (EnumDyeColor color : EnumDyeColor.values()) {
            RecipeHelper.addShapedRecipe(new ItemStack(colored_clear_glass, 1, color.getMetadata()),
                    "ggg", "gdg", "ggg",
                    'g', new ItemStack(clear_glass),
                    'd', new ItemStack(Items.DYE, 1, color.getDyeDamage()));
        }
    }

    @Override
    public void setupConfig()
    {
        enable_colored_clear_glass = ConfigHelper.getBool(category, "Enable Colored Clear Glass", true);
    }
}
