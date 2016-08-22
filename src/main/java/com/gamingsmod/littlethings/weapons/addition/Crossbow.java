package com.gamingsmod.littlethings.weapons.addition;

import com.gamingsmod.littlethings.base.LittleThings;
import com.gamingsmod.littlethings.base.helper.RecipeHelper;
import com.gamingsmod.littlethings.base.item.ModItem;
import com.gamingsmod.littlethings.base.loader.Addition;
import com.gamingsmod.littlethings.weapons.entity.EntityCrossBolt;
import com.gamingsmod.littlethings.weapons.entity.EntityCrossBoltExplosive;
import com.gamingsmod.littlethings.weapons.entity.EntityCrossBoltPotion;
import com.gamingsmod.littlethings.weapons.entity.render.RenderCrossBolt;
import com.gamingsmod.littlethings.weapons.item.ItemCrossBolt;
import com.gamingsmod.littlethings.weapons.item.ItemCrossbow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Crossbow extends Addition
{
    public static ModItem crossbow;
    public static ModItem cross_bolt;

    @Override
    public void preInit(FMLPreInitializationEvent e)
    {
        crossbow = new ItemCrossbow();
        cross_bolt = new ItemCrossBolt();

        EntityRegistry.registerModEntity(EntityCrossBolt.class, "CrossBolt", 0, LittleThings.instance, 256, 10, true);
        EntityRegistry.registerModEntity(EntityCrossBoltExplosive.class, "CrossBolt_Explosive", 1, LittleThings.instance, 256, 10, true);
        EntityRegistry.registerModEntity(EntityCrossBoltPotion.class, "CrossBolt_Potion", 2, LittleThings.instance, 256, 10, true);
    }

    @Override
    public void init(FMLInitializationEvent e)
    {
        RecipeHelper.addShapedRecipe(crossbow,
                "ibi", "sss", " s ",
                'i', "ingotIron",
                'b', new ItemStack(Items.BOW),
                's', "stickWood"
        );

        RecipeHelper.addShapedRecipe(new ItemStack(cross_bolt, 8, 0), //NORMAL
                "i", "o", "o",
                'i', "ingotIron",
                'o', "obsidian"
        );

        RecipeHelper.addShapedRecipe(new ItemStack(cross_bolt, 2, 1), //EXPLOSIVE
                " t ", "tbt", " t ",
                't', new ItemStack(Blocks.TNT),
                'b', new ItemStack(cross_bolt, 1, 0)
        );

        RecipeHelper.addShapedRecipe(new ItemStack(cross_bolt, 2, 2), //SPEC
                " g ", "gbg", " g ",
                'g', "dustGlowstone",
                'b', new ItemStack(cross_bolt, 1, 0)
        );
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void clientPreInit(FMLPreInitializationEvent e)
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityCrossBolt.class, new RenderCrossBolt());
        RenderingRegistry.registerEntityRenderingHandler(EntityCrossBoltExplosive.class, new RenderCrossBolt());
        RenderingRegistry.registerEntityRenderingHandler(EntityCrossBoltPotion.class, new RenderCrossBolt());
    }
}
