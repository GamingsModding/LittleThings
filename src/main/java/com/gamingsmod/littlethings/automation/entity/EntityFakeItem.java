package com.gamingsmod.littlethings.automation.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityFakeItem extends EntityItem
{
    public EntityFakeItem(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
        setInfinitePickupDelay();
        motionX = 0;
        motionZ = 0;
    }

    public EntityFakeItem(World worldIn, double x, double y, double z, ItemStack stack)
    {
        super(worldIn, x, y, z, stack);
        setInfinitePickupDelay();
        motionX = 0;
        motionZ = 0;
    }

    public EntityFakeItem(World worldIn)
    {
        super(worldIn);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();
        this.motionX = 0;
        this.motionY = 0.20000000298023224D;
        this.motionZ = 0;
        Block block = worldObj.getBlockState(new BlockPos(this)).getBlock();
        TileEntity te = worldObj.getTileEntity(new BlockPos(this));
        if (te instanceof IInventory) {
            if (!worldObj.isRemote) {
                ItemStack left = TileEntityHopper.putStackInInventoryAllSlots((IInventory) te, getEntityItem(), EnumFacing.DOWN);
                if (left != null) {
                    EntityItem realItem = new EntityItem(worldObj, posX, posY + 1, posZ, left);
                    worldObj.spawnEntityInWorld(realItem);
                }
            }
            setDead();
        }

        if (!(block instanceof BlockGlass) && !(block instanceof BlockStainedGlass) && !isDead) {
            if (!worldObj.isRemote) {
                EntityItem realItem = new EntityItem(worldObj, posX, posY, posZ, getEntityItem());
                worldObj.spawnEntityInWorld(realItem);
            }
            setDead();
        }
    }

    @Override
    public void moveEntity(double x, double y, double z)
    {
        posX += x;
        posY += y;
        posZ += z;
    }

    @Override
    protected boolean pushOutOfBlocks(double x, double y, double z)
    {
        return false;
    }

    @Override
    public boolean func_189652_ae()
    {
        return true;
    }
}
