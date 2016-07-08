package com.gamingsmod.littlethings.base.network.message;

import com.gamingsmod.littlethings.base.network.Message;
import com.gamingsmod.littlethings.vanity.addition.NoConflictRecipes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.List;

public class MessageGuiRecipeConflictPlayer extends Message
{
    public int currentResult;

    public MessageGuiRecipeConflictPlayer()
    {
    }

    public MessageGuiRecipeConflictPlayer(int result)
    {
        currentResult = result;
    }

    @Override
    public IMessage handleMessage(MessageContext context)
    {
        EntityPlayer player = context.getServerHandler().playerEntity;
        Container openContainer = player.openContainer;
        if (openContainer instanceof ContainerPlayer) {
            ContainerPlayer workbench = (ContainerPlayer) openContainer;
            List<ItemStack> results = NoConflictRecipes.getResults(((ContainerPlayer) openContainer).craftMatrix, player.worldObj);
            if (results.size() <= 1)
                return null;
            workbench.craftResult.setInventorySlotContents(0, results.get(currentResult));
        }
        return null;
    }
}
