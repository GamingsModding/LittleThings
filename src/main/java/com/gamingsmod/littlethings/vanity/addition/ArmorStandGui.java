package com.gamingsmod.littlethings.vanity.addition;

import com.gamingsmod.littlethings.base.LittleThings;
import com.gamingsmod.littlethings.base.loader.Addition;
import com.gamingsmod.littlethings.base.network.GuiHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class ArmorStandGui extends Addition
{
    public static int armorStandInteraction = 0;

    public static EntityArmorStand getClosestArmorStand(Entity entityIn, World world, int distance)
    {
        if (!world.isRemote || armorStandInteraction != 0) {
            return (EntityArmorStand) world.getEntityByID(armorStandInteraction);
        }
        AxisAlignedBB theViewBoundingBox = new AxisAlignedBB(
                entityIn.posX - 0.5D,
                entityIn.posY - 0.0D,
                entityIn.posZ - 0.5D,
                entityIn.posX + 0.5D,
                entityIn.posY + 1.5D,
                entityIn.posZ + 0.5D
        );
        RayTraceResult returnMOP = null;
        if (world != null) {
            double var2 = distance;
            returnMOP = entityIn.rayTrace(var2, 0);
            double calcdist = var2;
            Vec3d pos = entityIn.getPositionEyes(0);
            var2 = calcdist;
            if (returnMOP != null) {
                calcdist = returnMOP.hitVec.distanceTo(pos);
            }

            Vec3d lookvec = entityIn.getLook(0);
            Vec3d var8 = pos.addVector(lookvec.xCoord * var2,
                    lookvec.yCoord * var2,
                    lookvec.zCoord * var2);
            Entity pointedEntity = null;
            float var9 = 1.0F;
            @SuppressWarnings("unchecked")
            List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(
                    entityIn,
                    theViewBoundingBox.addCoord(
                            lookvec.xCoord * var2,
                            lookvec.yCoord * var2,
                            lookvec.zCoord * var2).expand(var9, var9, var9));
            double d = calcdist;

            for (Entity entity : list) {
                if (entity.canBeCollidedWith()) {
                    float bordersize = entity.getCollisionBorderSize();
                    AxisAlignedBB aabb = new AxisAlignedBB(
                            entity.posX - entity.width / 2,
                            entity.posY,
                            entity.posZ - entity.width / 2,
                            entity.posX + entity.width / 2,
                            entity.posY + entity.height,
                            entity.posZ + entity.width / 2);
                    aabb.expand(bordersize, bordersize, bordersize);
                    RayTraceResult mop0 = aabb.calculateIntercept(pos, var8);

                    if (aabb.isVecInside(pos)) {
                        if (0.0D < d || d == 0.0D) {
                            pointedEntity = entity;
                            d = 0.0D;
                        }
                    } else if (mop0 != null) {
                        double d1 = pos.distanceTo(mop0.hitVec);

                        if (d1 < d || d == 0.0D) {
                            pointedEntity = entity;
                            d = d1;
                        }
                    }
                }
            }

            if (pointedEntity != null && (d < calcdist || returnMOP == null) && pointedEntity instanceof EntityArmorStand) {
                returnMOP = new RayTraceResult(pointedEntity);
            }
        }
        return (EntityArmorStand) returnMOP.entityHit;
    }

    @SubscribeEvent
    public void onEntitySpec(PlayerInteractEvent.EntityInteractSpecific e)
    {
        Entity target = e.getTarget();
        if (target instanceof EntityArmorStand) {
            if (e.getEntityPlayer().isSneaking()) {
                armorStandInteraction = e.getTarget().getEntityId();
                e.getEntityPlayer().openGui(LittleThings.instance, GuiHandler.ARMOR_STAND, e.getWorld(), (int) target.posX, (int) target.posY, (int) target.posZ);

                e.setCanceled(true);
            }
        }
    }

    @Override
    public boolean hasSubscriptions()
    {
        return true;
    }
}
