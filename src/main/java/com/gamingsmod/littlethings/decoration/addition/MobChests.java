package com.gamingsmod.littlethings.decoration.addition;

import com.gamingsmod.littlethings.base.block.ModBlock;
import com.gamingsmod.littlethings.base.helper.NBTHelper;
import com.gamingsmod.littlethings.base.item.ModItem;
import com.gamingsmod.littlethings.base.lib.LibMisc;
import com.gamingsmod.littlethings.base.loader.Addition;
import com.gamingsmod.littlethings.decoration.block.BlockMobChests;
import com.gamingsmod.littlethings.decoration.item.ItemMobChest;
import com.gamingsmod.littlethings.decoration.tileentity.TileEntityMobChest;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.HashMap;
import java.util.Random;

public class MobChests extends Addition
{
    public static HashMap<String, SoundEvent> OPEN_SOUND = new HashMap<>();
    public static HashMap<String, SoundEvent> CLOSE_SOUND = new HashMap<>();
    public static HashMap<Class<? extends Entity>, String> ENTITY_REGISTER = new HashMap<>();

    public static ModBlock mob_chest;
    public static ModItem mob_chest_item;

    public MobChests() {
        register("cow", SoundEvents.ENTITY_COW_AMBIENT, SoundEvents.ENTITY_COW_DEATH, EntityCow.class);
        register("pig", SoundEvents.ENTITY_PIG_AMBIENT, SoundEvents.ENTITY_PIG_DEATH, EntityPig.class);
        register("sheep", SoundEvents.ENTITY_SHEEP_AMBIENT, SoundEvents.ENTITY_SHEEP_DEATH, EntitySheep.class);
        register("chicken", SoundEvents.ENTITY_CHICKEN_AMBIENT, SoundEvents.ENTITY_CHICKEN_DEATH, EntityChicken.class);

        register("creeper", SoundEvents.ENTITY_CREEPER_PRIMED, SoundEvents.ENTITY_CREEPER_DEATH, EntityCreeper.class);
        register("skeleton", SoundEvents.ENTITY_SKELETON_AMBIENT, SoundEvents.ENTITY_SKELETON_DEATH, EntitySkeleton.class);
        register("spider", SoundEvents.ENTITY_SPIDER_AMBIENT, SoundEvents.ENTITY_SPIDER_DEATH, EntitySpider.class);
        register("zombie", SoundEvents.ENTITY_ZOMBIE_AMBIENT, SoundEvents.ENTITY_ZOMBIE_DEATH, EntityZombie.class);

//        register("dog", SoundEvents.ENTITY_WOLF_HOWL, SoundEvents.ENTITY_WOLF_WHINE);
        register("squid", SoundEvents.ENTITY_SQUID_AMBIENT, SoundEvents.ENTITY_SQUID_DEATH, EntitySquid.class);
    }

    @Override
    public void preInit(FMLPreInitializationEvent e)
    {
        mob_chest = new BlockMobChests();
        mob_chest_item = new ItemMobChest();

        GameRegistry.registerTileEntity(TileEntityMobChest.class, LibMisc.PREFIX_MOD + "mob_chest");
    }

    private void register(String mob, SoundEvent open, SoundEvent close, Class<? extends Entity> clazz)
    {
        OPEN_SOUND.put(mob, open);
        CLOSE_SOUND.put(mob, close);
        ENTITY_REGISTER.put(clazz, mob);
    }

    @SubscribeEvent
    public void onMobDeathDrops(LivingDropsEvent event)
    {
        Entity dead = event.getEntity();
        Random RNG = new Random();
        if (event.getSource().getSourceOfDamage() instanceof EntityPlayer) {
            if (ENTITY_REGISTER.containsKey(dead.getClass()) && RNG.nextFloat() <= 0.1) {
                ItemStack stack = new ItemStack(mob_chest_item, 1, 0);
                NBTHelper.setString(stack, BlockMobChests.NBT_MOB_TAG, ENTITY_REGISTER.getOrDefault(dead.getClass(), "cow"));

                event.getDrops().add(new EntityItem(dead.worldObj, dead.posX, dead.posY, dead.posZ, stack));
            }
        }
    }

    @Override
    public boolean hasSubscriptions()
    {
        return true;
    }
}
