package com.gamingsmod.littlethings.automation.tileentity;

import com.gamingsmod.littlethings.automation.entity.EntityFakeItem;
import com.gamingsmod.littlethings.base.tileentity.ModTileInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ITickable;

public class TileEntityItemElevator extends ModTileInventory implements ITickable
{
    private int previousRedstone = 0;

    @Override
    public String getName()
    {
        return hasCustomName() ? getCustomName() : "container.littlethings.item_elevator";
    }

    @Override
    public int getSizeInventory()
    {
        return 9;
    }

    @Override
    public void update()
    {
        int currentRedstone = worldObj.isBlockIndirectlyGettingPowered(getPos());

        if (previousRedstone == 0 && currentRedstone != 0) {
            if (!worldObj.isRemote) this.updateElevator();
        }
        previousRedstone = currentRedstone;
    }

    private void updateElevator()
    {
        for (int i = 0; i < getSizeInventory(); i++) {
            ItemStack stack = getStackInSlot(i);

            if (stack != null) {
                moveItem(stack.splitStack(1));
                if (stack.stackSize <= 0)
                    setInventorySlotContents(i, null);
                break;
            }
        }
    }

    private void moveItem(ItemStack move)
    {
        EntityFakeItem fake = new EntityFakeItem(worldObj, this.pos.getX() + 0.5, this.pos.getY() + 1, this.pos.getZ() + 0.5, move);
        worldObj.spawnEntityInWorld(fake);
    }
}
