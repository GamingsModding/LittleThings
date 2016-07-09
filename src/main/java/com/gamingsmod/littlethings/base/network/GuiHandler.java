package com.gamingsmod.littlethings.base.network;

import com.gamingsmod.littlethings.automation.container.ContainerItemElevator;
import com.gamingsmod.littlethings.automation.gui.container.GuiItemElevator;
import com.gamingsmod.littlethings.automation.tileentity.TileEntityItemElevator;
import com.gamingsmod.littlethings.decoration.container.ContainerCraftingTable;
import com.gamingsmod.littlethings.decoration.gui.container.GuiCraftingTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
    public static final int CRAFTING_TABLE_NO_CONFLICT = 0;
    public static final int ITEM_ELEVATOR = 1;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID) {
            case CRAFTING_TABLE_NO_CONFLICT:
                return new ContainerCraftingTable(player.inventory, world, new BlockPos(x, y, z));
            case ITEM_ELEVATOR:
                return new ContainerItemElevator(player.inventory, (TileEntityItemElevator) world.getTileEntity(new BlockPos(x, y, z)));
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID) {
            case CRAFTING_TABLE_NO_CONFLICT:
                return new GuiCraftingTable(player.inventory, world, new BlockPos(x, y, z));
            case ITEM_ELEVATOR:
                return new GuiItemElevator(player.inventory, (TileEntityItemElevator) world.getTileEntity(new BlockPos(x,y,z)));
        }
        return null;
    }
}
