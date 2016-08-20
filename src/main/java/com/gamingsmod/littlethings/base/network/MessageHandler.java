package com.gamingsmod.littlethings.base.network;

import com.gamingsmod.littlethings.base.lib.LibMisc;
import com.gamingsmod.littlethings.base.network.message.MessageArmorStandGui;
import com.gamingsmod.littlethings.base.network.message.MessageGuiRecipeConflict;
import com.gamingsmod.littlethings.base.network.message.MessageGuiRecipeConflictPlayer;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class MessageHandler
{
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(LibMisc.MOD_NAME);

    private static int i = 0;

    public static void init()
    {
        register(MessageGuiRecipeConflict.class, Side.SERVER);
        register(MessageGuiRecipeConflictPlayer.class, Side.SERVER);
        register(MessageArmorStandGui.class, Side.SERVER);
    }

    @SuppressWarnings("unchecked")
    private static void register(Class clazz, Side handlerSide)
    {
        INSTANCE.registerMessage(clazz, clazz, i++, handlerSide);
    }
}
