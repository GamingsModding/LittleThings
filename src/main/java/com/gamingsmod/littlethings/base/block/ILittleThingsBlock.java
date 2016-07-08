package com.gamingsmod.littlethings.base.block;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ILittleThingsBlock
{
    @SideOnly(Side.CLIENT)
    public void registerRender();

    @SideOnly(Side.CLIENT)
    public void registerVariantModels();

    void registerItemModel(int meta, String name);
}
