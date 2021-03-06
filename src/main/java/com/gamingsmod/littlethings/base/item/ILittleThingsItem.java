package com.gamingsmod.littlethings.base.item;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ILittleThingsItem
{
    @SideOnly(Side.CLIENT)
    void registerItemVariants();

    @SideOnly(Side.CLIENT)
    void registerRender();

    void registerItemModel(int meta, String name);

    default ItemMeshDefinition registerCustomMeshDefinition()
    {
        return null;
    }
}
