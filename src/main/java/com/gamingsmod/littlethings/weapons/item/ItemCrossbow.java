package com.gamingsmod.littlethings.weapons.item;

import com.gamingsmod.littlethings.base.item.ModItem;
import com.gamingsmod.littlethings.weapons.entity.EntityCrossBolt;
import com.gamingsmod.littlethings.weapons.entity.EntityCrossBoltExplosive;
import com.gamingsmod.littlethings.weapons.entity.EntityCrossBoltPotion;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemCrossbow extends ModItem
{
    public ItemCrossbow()
    {
        super();
        setUnlocalizedName("crossbow");
        setCreativeTab(CreativeTabs.COMBAT);
        setMaxDamage(350);
        setMaxStackSize(1);
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        ItemStack ammo = getAmmo(playerIn);

        if ((playerIn.isCreative() || ammo != null)) {
            worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) * 0.5F);
            if (!worldIn.isRemote) {
                EntityCrossBolt entityCrossBolt = null;
                if (ammo != null) {
                    if (ammo.getMetadata() == 0)
                        entityCrossBolt = new EntityCrossBolt(worldIn, playerIn);
                    else if (ammo.getMetadata() == 1)
                        entityCrossBolt = new EntityCrossBoltExplosive(worldIn, playerIn);
                    else if (ammo.getMetadata() == 2)
                        entityCrossBolt = (new EntityCrossBoltPotion(worldIn, playerIn, ammo)).setPotionEffect(MobEffects.GLOWING);

                } else if (playerIn.isCreative()) {
                    entityCrossBolt = new EntityCrossBolt(worldIn, playerIn);
                }

                if (entityCrossBolt != null) {
                    entityCrossBolt.setAim(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 3.0F, 1.0F);
                    worldIn.spawnEntityInWorld(entityCrossBolt);

                    if (!playerIn.isCreative() && ammo != null) ammo.stackSize--;

                    if (ammo != null && ammo.stackSize <= 0)
                        playerIn.inventory.deleteStack(ammo);

                    playerIn.getCooldownTracker().setCooldown(this, 10);
                    itemStackIn.damageItem(1, playerIn);
                }
            }
        }

        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }

    private ItemStack getAmmo(EntityPlayer player)
    {
        if (this.isValidAmmo(player.getHeldItem(EnumHand.OFF_HAND))) {
            return player.getHeldItem(EnumHand.OFF_HAND);
        } else if (this.isValidAmmo(player.getHeldItem(EnumHand.MAIN_HAND))) {
            return player.getHeldItem(EnumHand.MAIN_HAND);
        } else {
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                ItemStack itemstack = player.inventory.getStackInSlot(i);

                if (this.isValidAmmo(itemstack)) {
                    return itemstack;
                }
            }

            return null;
        }
    }

    protected boolean isValidAmmo(ItemStack stack)
    {
        return stack != null && stack.getItem() instanceof ItemCrossBolt;
    }
}
