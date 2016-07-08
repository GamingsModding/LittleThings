package com.gamingsmod.littlethings.decoration.tileentity;

import com.gamingsmod.littlethings.decoration.block.BlockMetalFurnace;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class TileEntityMetalFurnace extends TileEntityLockable implements ITickable, ISidedInventory
{
    private static final int[] SLOTS_TOP = new int[]{0};
    private static final int[] SLOTS_BOTTOM = new int[]{2, 1};
    private static final int[] SLOTS_SIDES = new int[]{1};
    net.minecraftforge.items.IItemHandler handlerTop = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.UP);
    net.minecraftforge.items.IItemHandler handlerBottom = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.DOWN);
    net.minecraftforge.items.IItemHandler handlerSide = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.WEST);
    private ItemStack[] furnaceItemStacks = new ItemStack[3];
    private int furnaceBurnTime;
    private int currentItemBurnTime;
    private int cookTime;
    private int totalCookTime;
    private String furnaceCustomName;
    private int customBurnTime;

    public TileEntityMetalFurnace()
    {
    }

    public TileEntityMetalFurnace(int burnTime)
    {
        customBurnTime = burnTime;
    }

    public static void func_189676_a(DataFixer p_189676_0_)
    {
        p_189676_0_.registerWalker(FixTypes.BLOCK_ENTITY, new ItemStackDataLists("Furnace", "Items"));
    }

    @SideOnly(Side.CLIENT)
    public static boolean isBurning(IInventory inventory)
    {
        return inventory.getField(0) > 0;
    }

    public static int getItemBurnTime(ItemStack stack)
    {
        if (stack == null) {
            return 0;
        } else {
            Item item = stack.getItem();

            if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.AIR) {
                Block block = Block.getBlockFromItem(item);

                if (block == Blocks.WOODEN_SLAB) {
                    return 150;
                }

                if (block.getDefaultState().getMaterial() == Material.WOOD) {
                    return 300;
                }

                if (block == Blocks.COAL_BLOCK) {
                    return 16000;
                }
            }

            if (item instanceof ItemTool && "WOOD".equals(((ItemTool) item).getToolMaterialName())) return 200;
            if (item instanceof ItemSword && "WOOD".equals(((ItemSword) item).getToolMaterialName())) return 200;
            if (item instanceof ItemHoe && "WOOD".equals(((ItemHoe) item).getMaterialName())) return 200;
            if (item == Items.STICK) return 100;
            if (item == Items.COAL) return 1600;
            if (item == Items.LAVA_BUCKET) return 20000;
            if (item == Item.getItemFromBlock(Blocks.SAPLING)) return 100;
            if (item == Items.BLAZE_ROD) return 2400;
            return net.minecraftforge.fml.common.registry.GameRegistry.getFuelValue(stack);
        }
    }

    public static boolean isItemFuel(ItemStack stack)
    {
        return getItemBurnTime(stack) > 0;
    }

    /**
     * Returns the number of slots in the inventory.
     */
    @Override
    public int getSizeInventory()
    {
        return this.furnaceItemStacks.length;
    }

    /**
     * Returns the stack in the given slot.
     */
    @Override
    @Nullable
    public ItemStack getStackInSlot(int index)
    {
        return this.furnaceItemStacks[index];
    }

    @Override
    @Nullable
    public ItemStack decrStackSize(int index, int count)
    {
        return ItemStackHelper.getAndSplit(this.furnaceItemStacks, index, count);
    }

    @Override
    @Nullable
    public ItemStack removeStackFromSlot(int index)
    {
        return ItemStackHelper.getAndRemove(this.furnaceItemStacks, index);
    }

    @Override
    public void setInventorySlotContents(int index, @Nullable ItemStack stack)
    {
        boolean flag = stack != null && stack.isItemEqual(this.furnaceItemStacks[index]) && ItemStack.areItemStackTagsEqual(stack, this.furnaceItemStacks[index]);
        this.furnaceItemStacks[index] = stack;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
            stack.stackSize = this.getInventoryStackLimit();
        }

        if (index == 0 && !flag) {
            this.totalCookTime = this.getCookTime(stack);
            this.cookTime = 0;
            this.markDirty();
        }
    }

    @Override
    public String getName()
    {
        return this.hasCustomName() ? this.furnaceCustomName : "container.furnace";
    }

    @Override
    public boolean hasCustomName()
    {
        return this.furnaceCustomName != null && !this.furnaceCustomName.isEmpty();
    }

    public void setCustomInventoryName(String p_145951_1_)
    {
        this.furnaceCustomName = p_145951_1_;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        NBTTagList nbttaglist = compound.getTagList("Items", 10);
        this.furnaceItemStacks = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound.getByte("Slot");

            if (j >= 0 && j < this.furnaceItemStacks.length) {
                this.furnaceItemStacks[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
            }
        }

        this.furnaceBurnTime = compound.getInteger("BurnTime");
        this.cookTime = compound.getInteger("CookTime");
        this.totalCookTime = compound.getInteger("CookTimeTotal");
        this.customBurnTime = compound.getInteger("CustomBurnTime");
        this.currentItemBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);

        if (compound.hasKey("CustomName", 8)) {
            this.furnaceCustomName = compound.getString("CustomName");
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("BurnTime", this.furnaceBurnTime);
        compound.setInteger("CookTime", this.cookTime);
        compound.setInteger("CookTimeTotal", this.totalCookTime);
        compound.setInteger("CustomBurnTime", this.customBurnTime);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.furnaceItemStacks.length; ++i) {
            if (this.furnaceItemStacks[i] != null) {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte) i);
                this.furnaceItemStacks[i].writeToNBT(nbttagcompound);
                nbttaglist.appendTag(nbttagcompound);
            }
        }

        compound.setTag("Items", nbttaglist);

        if (this.hasCustomName()) {
            compound.setString("CustomName", this.furnaceCustomName);
        }

        return compound;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    public boolean isBurning()
    {
        return this.furnaceBurnTime > 0;
    }

    @Override
    public void update()
    {
        boolean flag = this.isBurning();
        boolean flag1 = false;

        if (this.isBurning()) {
            --this.furnaceBurnTime;
        }

        if (!this.worldObj.isRemote) {
            if (this.isBurning() || this.furnaceItemStacks[1] != null && this.furnaceItemStacks[0] != null) {
                if (!this.isBurning() && this.canSmelt()) {
                    this.furnaceBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);
                    this.currentItemBurnTime = this.furnaceBurnTime;

                    if (this.isBurning()) {
                        flag1 = true;

                        if (this.furnaceItemStacks[1] != null) {
                            --this.furnaceItemStacks[1].stackSize;

                            if (this.furnaceItemStacks[1].stackSize == 0) {
                                this.furnaceItemStacks[1] = furnaceItemStacks[1].getItem().getContainerItem(furnaceItemStacks[1]);
                            }
                        }
                    }
                }

                if (this.isBurning() && this.canSmelt()) {
                    ++this.cookTime;

                    if (this.cookTime == this.totalCookTime) {
                        this.cookTime = 0;
                        this.totalCookTime = this.getCookTime(this.furnaceItemStacks[0]);
                        this.smeltItem();
                        flag1 = true;
                    }
                } else {
                    this.cookTime = 0;
                }
            } else if (!this.isBurning() && this.cookTime > 0) {
                this.cookTime = MathHelper.clamp_int(this.cookTime - 2, 0, this.totalCookTime);
            }

            if (flag != this.isBurning()) {
                flag1 = true;
                BlockMetalFurnace.setState(this.isBurning(), this.worldObj, this.pos);
            }
        }

        if (flag1) {
            this.markDirty();
        }
    }

    public int getCookTime(@Nullable ItemStack stack)
    {
        return customBurnTime;
    }

    private boolean canSmelt()
    {
        if (this.furnaceItemStacks[0] == null) {
            return false;
        } else {
            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.furnaceItemStacks[0]);
            if (itemstack == null) return false;
            if (this.furnaceItemStacks[2] == null) return true;
            if (!this.furnaceItemStacks[2].isItemEqual(itemstack)) return false;
            int result = furnaceItemStacks[2].stackSize + itemstack.stackSize;
            return result <= getInventoryStackLimit() && result <= this.furnaceItemStacks[2].getMaxStackSize(); //Forge BugFix: Make it respect stack sizes properly.
        }
    }

    public void smeltItem()
    {
        if (this.canSmelt()) {
            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.furnaceItemStacks[0]);

            if (this.furnaceItemStacks[2] == null) {
                this.furnaceItemStacks[2] = itemstack.copy();
            } else if (this.furnaceItemStacks[2].getItem() == itemstack.getItem()) {
                this.furnaceItemStacks[2].stackSize += itemstack.stackSize; // Forge BugFix: Results may have multiple items
            }

            if (this.furnaceItemStacks[0].getItem() == Item.getItemFromBlock(Blocks.SPONGE) && this.furnaceItemStacks[0].getMetadata() == 1 && this.furnaceItemStacks[1] != null && this.furnaceItemStacks[1].getItem() == Items.BUCKET) {
                this.furnaceItemStacks[1] = new ItemStack(Items.WATER_BUCKET);
            }

            --this.furnaceItemStacks[0].stackSize;

            if (this.furnaceItemStacks[0].stackSize <= 0) {
                this.furnaceItemStacks[0] = null;
            }
        }
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return this.worldObj.getTileEntity(this.pos) == this && player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory(EntityPlayer player)
    {
    }

    @Override
    public void closeInventory(EntityPlayer player)
    {
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        if (index == 2) {
            return false;
        } else if (index != 1) {
            return true;
        } else {
            ItemStack itemstack = this.furnaceItemStacks[1];
            return isItemFuel(stack) || SlotFurnaceFuel.isBucket(stack) && (itemstack == null || itemstack.getItem() != Items.BUCKET);
        }
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side)
    {
        return side == EnumFacing.DOWN ? SLOTS_BOTTOM : (side == EnumFacing.UP ? SLOTS_TOP : SLOTS_SIDES);
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
    {
        return this.isItemValidForSlot(index, itemStackIn);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
    {
        if (direction == EnumFacing.DOWN && index == 1) {
            Item item = stack.getItem();

            if (item != Items.WATER_BUCKET && item != Items.BUCKET) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String getGuiID()
    {
        return "minecraft:furnace";
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        return new ContainerFurnace(playerInventory, this);
    }

    @Override
    public int getField(int id)
    {
        switch (id) {
            case 0:
                return this.furnaceBurnTime;
            case 1:
                return this.currentItemBurnTime;
            case 2:
                return this.cookTime;
            case 3:
                return this.totalCookTime;
            default:
                return 0;
        }
    }

    @Override
    public void setField(int id, int value)
    {
        switch (id) {
            case 0:
                this.furnaceBurnTime = value;
                break;
            case 1:
                this.currentItemBurnTime = value;
                break;
            case 2:
                this.cookTime = value;
                break;
            case 3:
                this.totalCookTime = value;
        }
    }

    @Override
    public int getFieldCount()
    {
        return 4;
    }

    @Override
    public void clear()
    {
        for (int i = 0; i < this.furnaceItemStacks.length; ++i) {
            this.furnaceItemStacks[i] = null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, net.minecraft.util.EnumFacing facing)
    {
        if (facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            if (facing == EnumFacing.DOWN)
                return (T) handlerBottom;
            else if (facing == EnumFacing.UP)
                return (T) handlerTop;
            else
                return (T) handlerSide;
        return super.getCapability(capability, facing);
    }
}