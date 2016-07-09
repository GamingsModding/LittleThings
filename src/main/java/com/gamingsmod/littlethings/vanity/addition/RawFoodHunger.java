package com.gamingsmod.littlethings.vanity.addition;

import com.gamingsmod.littlethings.base.loader.Addition;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class RawFoodHunger extends Addition
{
    public static List<Item> foodItemStacks;

    static {
        foodItemStacks = new ArrayList<>();

        addToItems(Items.BEEF);
        addToItems(Items.PORKCHOP);
        addToItems(Items.MUTTON);
        addToItems(Items.CHICKEN);
        addToItems(Items.FISH);
        addToItems(Items.RABBIT);
    }

    public static void addToItems(Item stack)
    {
        foodItemStacks.add(stack);
    }

    @SubscribeEvent
    public void onFoodFinished(LivingEntityUseItemEvent.Finish e)
    {
        if (e.getEntity() instanceof EntityPlayer && e.getItem() != null && e.getItem().getItem() instanceof ItemFood) {
            ItemStack food = e.getItem();
            World world = e.getEntity().getEntityWorld();

            if (!world.isRemote)
                if (foodItemStacks.contains(food.getItem()))
                    if (world.rand.nextFloat() > 0.3F)
                        ((EntityPlayer) e.getEntity()).addPotionEffect(new PotionEffect(MobEffects.HUNGER, 600, 0));
        }
    }

    @Override
    public boolean hasSubscriptions()
    {
        return true;
    }
}
