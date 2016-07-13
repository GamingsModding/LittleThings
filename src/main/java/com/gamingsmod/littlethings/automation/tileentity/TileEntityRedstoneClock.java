package com.gamingsmod.littlethings.automation.tileentity;

import com.gamingsmod.littlethings.automation.block.BlockRedstoneClock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityRedstoneClock extends TileEntity implements ITickable
{
    private int counter;

    @Override
    public void update()
    {
        IBlockState currentState = worldObj.getBlockState(getPos());
        if (worldObj.getStrongPower(getPos()) >= 1 && !currentState.getValue(BlockRedstoneClock.TRIGGERED)) return;

        counter++;
        if (!currentState.getValue(BlockRedstoneClock.TRIGGERED)) {
            if (counter >= 5) {
                worldObj.setBlockState(getPos(), currentState.withProperty(BlockRedstoneClock.TRIGGERED, true));
                counter = 0;
            }
        } else {
//            if (counter >= 4) {
                worldObj.setBlockState(getPos(), currentState.withProperty(BlockRedstoneClock.TRIGGERED, false));
//                counter = 0;
//            }
        }
    }
}