package com.gamingsmod.littlethings.decoration.tileentity;

import com.gamingsmod.littlethings.base.tileentity.ModTileInventory;
import com.gamingsmod.littlethings.decoration.addition.MobChests;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;

public class TileEntityMobChest extends ModTileInventory implements ITickable
{
    public float lidAngle;
    public float prevLidAngle;
    public int numPlayersUsing;
    private int ticksSinceSync;
    private String mob = "cow";

    @Override
    public int getSizeInventory()
    {
        return 27;
    }

    @Override
    public String getName()
    {
        return hasCustomName() ? getCustomName() : "container.littlethings.mob_chest";
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        nbt = super.writeToNBT(nbt);

        nbt.setString("mob", mob);

        return nbt;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        mob = nbt.getString("mob");
    }

    @Override
    public NBTTagCompound getUpdateTag()
    {
        NBTTagCompound compound = super.getUpdateTag();
        compound.setString("mob", mob);
        return compound;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag)
    {
        super.handleUpdateTag(tag);
        mob = tag.getString("mob");
    }

    public String getMob()
    {
        return mob;
    }

    public void setMob(String mob)
    {
        if (mob.equals("")) this.mob = "cow";
        else this.mob = mob;
    }

    @Override
    public void update()
    {
        if (++this.ticksSinceSync % 20 * 4 == 0)
            this.worldObj.addBlockEvent(this.pos, Blocks.ENDER_CHEST, 1, this.numPlayersUsing);

        this.prevLidAngle = this.lidAngle;
        int i = this.pos.getX();
        int j = this.pos.getY();
        int k = this.pos.getZ();
        float f = 0.1F;

        if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F) {
            double d0 = (double) i + 0.5D;
            double d1 = (double) k + 0.5D;
            this.worldObj.playSound(null, d0, (double) j + 0.5D, d1, MobChests.OPEN_SOUND.get(mob), SoundCategory.BLOCKS, 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
        }

        if (this.numPlayersUsing == 0 && this.lidAngle > 0.0F || this.numPlayersUsing > 0 && this.lidAngle < 1.0F) {
            float f2 = this.lidAngle;

            if (this.numPlayersUsing > 0) this.lidAngle += 0.1F;
            else this.lidAngle -= 0.1F;

            if (this.lidAngle > 1.0F) this.lidAngle = 1.0F;

            float f1 = 0.5F;

            if (this.lidAngle < 0.5F && f2 >= 0.5F) {
                double d3 = (double) i + 0.5D;
                double d2 = (double) k + 0.5D;
                this.worldObj.playSound(null, d3, (double) j + 0.5D, d2, MobChests.CLOSE_SOUND.get(mob), SoundCategory.BLOCKS, 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
            }

            if (this.lidAngle < 0.0F) this.lidAngle = 0.0F;
        }
    }

    @Override
    public boolean receiveClientEvent(int id, int type)
    {
        if (id == 1) {
            this.numPlayersUsing = type;
            return true;
        } else {
            return super.receiveClientEvent(id, type);
        }
    }

    @Override
    public void openInventory(EntityPlayer player)
    {
        openChest();
    }

    @Override
    public void closeInventory(EntityPlayer player)
    {
        closeChest();
    }

    @Override
    public void invalidate()
    {
        this.updateContainingBlockInfo();
        super.invalidate();
    }

    public void openChest()
    {
        ++this.numPlayersUsing;
        this.worldObj.addBlockEvent(this.pos, MobChests.mob_chest, 1, this.numPlayersUsing);
    }

    public void closeChest()
    {
        --this.numPlayersUsing;
        this.worldObj.addBlockEvent(this.pos, MobChests.mob_chest, 1, this.numPlayersUsing);
    }

    public boolean canBeUsed(EntityPlayer player)
    {
        return this.worldObj.getTileEntity(this.pos) == this && player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
    }

    @Override
    public boolean canRenderBreaking()
    {
        return true;
    }
}
