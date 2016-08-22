package com.gamingsmod.littlethings.base.network;

import com.gamingsmod.littlethings.automation.container.ContainerItemElevator;
import com.gamingsmod.littlethings.automation.gui.container.GuiItemElevator;
import com.gamingsmod.littlethings.automation.tileentity.TileEntityItemElevator;
import com.gamingsmod.littlethings.decoration.container.ContainerCraftingTable;
import com.gamingsmod.littlethings.decoration.gui.container.GuiCraftingTable;
import com.gamingsmod.littlethings.vanity.addition.ArmorStandGui;
import com.gamingsmod.littlethings.vanity.container.ContainerArmorStand;
import com.gamingsmod.littlethings.vanity.gui.GuiArmorStandRotation;
import com.gamingsmod.littlethings.vanity.gui.container.GuiArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
    public static final int CRAFTING_TABLE_NO_CONFLICT = 0;
    public static final int ITEM_ELEVATOR = 1;
    public static final int ARMOR_STAND = 2;
    public static final int ARMOR_STAND_ROTATION = 3;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID) {
            case CRAFTING_TABLE_NO_CONFLICT:
                return new ContainerCraftingTable(player.inventory, world, new BlockPos(x, y, z));
            case ITEM_ELEVATOR:
                return new ContainerItemElevator(player.inventory, (TileEntityItemElevator) world.getTileEntity(new BlockPos(x, y, z)));
            case ARMOR_STAND:
                return new ContainerArmorStand(player.inventory, ArmorStandGui.getClosestArmorStand(player, world, 5), player);
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
            case ARMOR_STAND:
                return new GuiArmorStand(player.inventory, ArmorStandGui.getClosestArmorStand(player, world, 5), player);
            case ARMOR_STAND_ROTATION:
                return new GuiArmorStandRotation(ArmorStandGui.getClosestArmorStand(player, world, 5), player);
        }
        return null;
    }
}
