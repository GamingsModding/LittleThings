package com.gamingsmod.littlethings.vanity.container;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class ContainerArmorStand extends Container
{
    private static final EntityEquipmentSlot[] VALID_EQUIPMENT_SLOTS = new EntityEquipmentSlot[]{EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET};
    private IInventory playerInventory;
    private EntityArmorStand armorStand;
    private IInventory armorStandInventory;
    private EntityPlayer player;

    public ContainerArmorStand(IInventory playerInventory, final EntityArmorStand armorStand, EntityPlayer player)
    {
        this.playerInventory = playerInventory;
        this.armorStand = armorStand;
        this.armorStandInventory = new ArmorStandInventory(armorStand);
        this.player = player;

        this.addSlotToContainer(new Slot(armorStandInventory, 0, 77, 44)
        {
            @Override
            public boolean isItemValid(@Nullable ItemStack stack)
            {
                return super.isItemValid(stack) && armorStand.getShowArms();
            }

            @Override
            @SideOnly(Side.CLIENT)
            public boolean canBeHovered()
            {
                return armorStand.getShowArms();
            }
        });

        for (int k = 0; k < 4; ++k) {
            final EntityEquipmentSlot entityequipmentslot = VALID_EQUIPMENT_SLOTS[k];
            this.addSlotToContainer(new Slot(armorStandInventory, 4 - k, 8, 8 + k * 18)
            {
                @Override
                public int getSlotStackLimit()
                {
                    return 1;
                }

                @Override
                public boolean isItemValid(@Nullable ItemStack stack)
                {
                    return stack != null && stack.getItem().isValidArmor(stack, entityequipmentslot, armorStand);
                }

                @Override
                @Nullable
                @SideOnly(Side.CLIENT)
                public String getSlotTexture()
                {
                    return ItemArmor.EMPTY_SLOT_NAMES[entityequipmentslot.getIndex()];
                }
            });
        }

        this.addSlotToContainer(new Slot(armorStandInventory, 5, 77, 62)
        {
            @Override
            public boolean isItemValid(@Nullable ItemStack stack)
            {
                return super.isItemValid(stack) && armorStand.getShowArms();
            }

            @Override
            public boolean canBeHovered()
            {
                return armorStand.getShowArms();
            }

            @Override
            @Nullable
            @SideOnly(Side.CLIENT)
            public String getSlotTexture()
            {
                return "minecraft:items/empty_armor_slot_shield";
            }
        });

        for (int l = 0; l < 3; ++l)
            for (int j1 = 0; j1 < 9; ++j1)
                this.addSlotToContainer(new Slot(playerInventory, j1 + (l + 1) * 9, 8 + j1 * 18, 84 + l * 18));

        for (int i1 = 0; i1 < 9; ++i1)
            this.addSlotToContainer(new Slot(playerInventory, i1, 8 + i1 * 18, 142));

        armorStandInventory.openInventory(player);
    }

    @Nullable
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = null;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            EntityEquipmentSlot entityequipmentslot = EntityLiving.getSlotForItemStack(itemstack);

            if (index == 0) {
                if (!this.mergeItemStack(itemstack1, 6, 42, true)) {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            } else if (index >= 0 && index <= 5) {
                if (!this.mergeItemStack(itemstack1, 6, 42, false)) {
                    return null;
                }
            } else if (entityequipmentslot.getSlotType() == EntityEquipmentSlot.Type.ARMOR && !this.inventorySlots.get(4 - entityequipmentslot.getIndex()).getHasStack()) {
                int i = 4 - entityequipmentslot.getIndex();

                if (!this.mergeItemStack(itemstack1, i, i + 1, false)) {
                    return null;
                }
            } else if (entityequipmentslot == EntityEquipmentSlot.OFFHAND && !this.inventorySlots.get(5).getHasStack()) {
                if (!this.mergeItemStack(itemstack1, 5, 6, false)) {
                    return null;
                }
            }

            if (itemstack1.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize) {
                return null;
            }

            slot.onPickupFromSlot(playerIn, itemstack1);
        }

        return itemstack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return this.armorStand.isEntityAlive() && this.armorStand.getDistanceToEntity(playerIn) < 8.0F;
    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn)
    {
        armorStandInventory.closeInventory(playerIn);
    }

    private class ArmorStandInventory implements IInventory
    {
        private final EntityArmorStand armorStand;
        private World world;
        private ItemStack[] inventory;

        public ArmorStandInventory(EntityArmorStand armorStand)
        {
            this.world = armorStand.worldObj;
            this.armorStand = armorStand;
            inventory = new ItemStack[getSizeInventory()];
        }

        @Override
        public int getSizeInventory()
        {
            return 6;
        }

        @Override
        public ItemStack getStackInSlot(int index)
        {
            if (index < 0 || index >= this.getSizeInventory())
                return null;
            return this.inventory[index];
        }

        @Override
        public ItemStack decrStackSize(int index, int count)
        {
            if (this.getStackInSlot(index) != null) {
                ItemStack itemstack;

                if (this.getStackInSlot(index).stackSize <= count) {
                    itemstack = this.getStackInSlot(index);
                    this.setInventorySlotContents(index, null);
                    this.markDirty();
                    return itemstack;
                } else {
                    itemstack = this.getStackInSlot(index).splitStack(count);

                    if (this.getStackInSlot(index).stackSize <= 0) {
                        this.setInventorySlotContents(index, null);
                    } else {
                        //Just to show that changes happened
                        this.setInventorySlotContents(index, this.getStackInSlot(index));
                    }

                    this.markDirty();
                    return itemstack;
                }
            } else {
                return null;
            }
        }

        @Override
        public ItemStack removeStackFromSlot(int index)
        {
            ItemStack stack = this.getStackInSlot(index);
            this.setInventorySlotContents(index, null);
            return stack;
        }

        @Override
        public void setInventorySlotContents(int index, ItemStack stack)
        {
            if (index < 0 || index >= this.getSizeInventory())
                return;

            if (stack != null && stack.stackSize > this.getInventoryStackLimit())
                stack.stackSize = this.getInventoryStackLimit();

            if (stack != null && stack.stackSize == 0)
                stack = null;

            this.inventory[index] = stack;
            this.markDirty();
        }

        @Override
        public int getInventoryStackLimit()
        {
            return 64;
        }

        @Override
        public void markDirty()
        {
            this.closeInventory(null);
        }

        @Override
        public boolean isUseableByPlayer(EntityPlayer player)
        {
            return player.getDistanceSq(new BlockPos(armorStand.posX, armorStand.posY, armorStand.posZ).add(0.5, 0.5, 0.5)) <= 64;
        }

        @Override
        public void openInventory(EntityPlayer player)
        {
            for (EntityEquipmentSlot slot : EntityEquipmentSlot.values()) {
                ItemStack stack = armorStand.getItemStackFromSlot(slot);
                inventory[slot.getSlotIndex()] = stack;
            }
        }

        @Override
        public void closeInventory(EntityPlayer player)
        {
            for (EntityEquipmentSlot slot : EntityEquipmentSlot.values()) {
                ItemStack stack = getStackInSlot(slot.getSlotIndex());
                armorStand.setItemStackToSlot(slot, stack);
            }
        }

        @Override
        public boolean isItemValidForSlot(int index, ItemStack stack)
        {
            return true;
        }

        @Override
        public int getField(int id)
        {
            return 0;
        }

        @Override
        public void setField(int id, int value)
        {
        }

        @Override
        public int getFieldCount()
        {
            return 0;
        }

        @Override
        public void clear()
        {
            for (int i = 0; i < this.getSizeInventory(); i++)
                this.setInventorySlotContents(i, null);
        }

        @Override
        public String getName()
        {
            return this.armorStand.getName();
        }

        @Override
        public boolean hasCustomName()
        {
            return this.armorStand.hasCustomName();
        }

        @Override
        public ITextComponent getDisplayName()
        {
            return this.armorStand.getDisplayName();
        }
    }
}
