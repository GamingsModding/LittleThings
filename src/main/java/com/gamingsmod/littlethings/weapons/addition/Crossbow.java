package com.gamingsmod.littlethings.weapons.addition;

import com.gamingsmod.littlethings.base.LittleThings;
import com.gamingsmod.littlethings.base.item.ModItem;
import com.gamingsmod.littlethings.base.loader.Addition;
import com.gamingsmod.littlethings.weapons.entity.EntityCrossBolt;
import com.gamingsmod.littlethings.weapons.entity.EntityCrossBoltExplosive;
import com.gamingsmod.littlethings.weapons.entity.EntityCrossBoltPotion;
import com.gamingsmod.littlethings.weapons.entity.render.RenderCrossBolt;
import com.gamingsmod.littlethings.weapons.item.ItemCrossBolt;
import com.gamingsmod.littlethings.weapons.item.ItemCrossbow;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

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
    public void clientPreInit(FMLPreInitializationEvent e)
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityCrossBolt.class, new RenderCrossBolt());
        RenderingRegistry.registerEntityRenderingHandler(EntityCrossBoltExplosive.class, new RenderCrossBolt());
        RenderingRegistry.registerEntityRenderingHandler(EntityCrossBoltPotion.class, new RenderCrossBolt());
    }
}
