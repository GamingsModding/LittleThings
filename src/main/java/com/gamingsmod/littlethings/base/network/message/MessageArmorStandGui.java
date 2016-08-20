package com.gamingsmod.littlethings.base.network.message;

import com.gamingsmod.littlethings.base.network.Message;
import com.gamingsmod.littlethings.vanity.addition.ArmorStandGui;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageArmorStandGui extends Message
{
    public int type;
    public boolean bool;

    public MessageArmorStandGui()
    {}

    public MessageArmorStandGui(int type, boolean bool)
    {
        this.type = type;
        this.bool = bool;
    }

    @Override
    public IMessage handleMessage(MessageContext context)
    {
        EntityArmorStand armorStand = ArmorStandGui.getClosestArmorStand(context.getServerHandler().playerEntity, context.getServerHandler().playerEntity.worldObj ,5);
        armorStand.getDataManager().set(EntityArmorStand.STATUS, this.setBit(armorStand.getDataManager().get(EntityArmorStand.STATUS), type, bool));
        return null;
    }

    private byte setBit(byte p_184797_1_, int p_184797_2_, boolean p_184797_3_)
    {
        if (p_184797_3_) p_184797_1_ = (byte)(p_184797_1_ | p_184797_2_);
        else p_184797_1_ = (byte)(p_184797_1_ & ~p_184797_2_);

        return p_184797_1_;
    }
}
