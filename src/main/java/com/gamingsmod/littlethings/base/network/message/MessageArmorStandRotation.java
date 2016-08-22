package com.gamingsmod.littlethings.base.network.message;

import com.gamingsmod.littlethings.base.network.Message;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Rotations;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageArmorStandRotation extends Message
{
    public int armorstand;
    public int change;
    public float x;
    public float y;
    public float z;

    public MessageArmorStandRotation()
    {}

    public MessageArmorStandRotation(int armorstand, int change, float x, float y, float z)
    {
        this.armorstand = armorstand;
        this.change = change;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public IMessage handleMessage(MessageContext context)
    {
        EntityPlayer player = context.getServerHandler().playerEntity;
        EntityArmorStand armorStand = (EntityArmorStand) player.worldObj.getEntityByID(armorstand);
        Rotations newRotation = new Rotations(x, y, z);
        switch (change) {
            case 0: //HEAD
                armorStand.setHeadRotation(newRotation);
                break;
            case 1: //BODY
                armorStand.setBodyRotation(newRotation);
                break;
            case 2: //RIGHT ARM
                armorStand.setRightArmRotation(newRotation);
                break;
            case 3: //LEFT ARM
                armorStand.setLeftArmRotation(newRotation);
                break;
            case 4: //RIGHT LEG
                armorStand.setRightLegRotation(newRotation);
                break;
            case 5: //LEFT LEG
                armorStand.setLeftLegRotation(newRotation);
                break;
        }
        return null;
    }
}
