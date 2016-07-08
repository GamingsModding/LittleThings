package com.gamingsmod.littlethings.base.network.message;

import com.gamingsmod.littlethings.base.network.Message;
import com.gamingsmod.littlethings.decoration.container.ContainerCraftingTable;
import net.minecraft.inventory.Container;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageGuiRecipeConflict extends Message
{
    public int index;

    public MessageGuiRecipeConflict()
    {
    }

    public MessageGuiRecipeConflict(int index)
    {
        this.index = index;
    }

    @Override
    public IMessage handleMessage(MessageContext context)
    {
        Container openContainer = context.getServerHandler().playerEntity.openContainer;
        if (openContainer instanceof ContainerCraftingTable) {
            ContainerCraftingTable workbench = (ContainerCraftingTable) openContainer;
            workbench.setCurrentResult(index);
        }
        return null;
    }
}
