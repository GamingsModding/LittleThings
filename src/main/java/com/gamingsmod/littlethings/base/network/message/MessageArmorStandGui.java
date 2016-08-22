package com.gamingsmod.littlethings.base.network.message;

import com.gamingsmod.littlethings.base.network.Message;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageArmorStandGui extends Message
{
    public int type;
    public boolean bool;
    public int armorstand;

    public MessageArmorStandGui()
    {}

    public MessageArmorStandGui(int armorstand, int type, boolean bool)
    {
        this.armorstand = armorstand;
        this.type = type;
        this.bool = bool;
    }

    @Override
    public IMessage handleMessage(MessageContext context)
    {
        EntityArmorStand armorStand = (EntityArmorStand) context.getServerHandler().playerEntity.worldObj.getEntityByID(armorstand);
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
