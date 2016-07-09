package com.gamingsmod.littlethings.automation.addition;

import com.gamingsmod.littlethings.automation.block.BlockItemElevator;
import com.gamingsmod.littlethings.automation.entity.EntityFakeItem;
import com.gamingsmod.littlethings.automation.entity.render.RenderEntityFakeItem;
import com.gamingsmod.littlethings.automation.tileentity.TileEntityItemElevator;
import com.gamingsmod.littlethings.base.LittleThings;
import com.gamingsmod.littlethings.base.block.ModBlock;
import com.gamingsmod.littlethings.base.helper.RecipeHelper;
import com.gamingsmod.littlethings.base.lib.LibMisc;
import com.gamingsmod.littlethings.base.loader.Addition;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemElevator extends Addition
{
    public static ModBlock item_elevator;

    @Override
    public void preInit(FMLPreInitializationEvent e)
    {
        item_elevator =  new BlockItemElevator();

        GameRegistry.registerTileEntity(TileEntityItemElevator.class, LibMisc.PREFIX_MOD + "item_elevator");
        EntityRegistry.registerModEntity(EntityFakeItem.class, "FakeItem", 3, LittleThings.instance, 256, 10, true);
    }

    @Override
    public void init(FMLInitializationEvent e)
    {
        RecipeHelper.addShapedRecipe(item_elevator,
                "coc", "odo", "coc",
                'c', new ItemStack(Blocks.COBBLESTONE),
                'o', new ItemStack(Blocks.OBSIDIAN),
                'd', new ItemStack(Blocks.DISPENSER));
    }

    @Override
    public void clientPreInit(FMLPreInitializationEvent e)
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityFakeItem.class, new RenderEntityFakeItem());
    }
}
