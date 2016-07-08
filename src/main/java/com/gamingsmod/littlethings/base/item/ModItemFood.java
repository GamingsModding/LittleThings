package com.gamingsmod.littlethings.base.item;

import com.gamingsmod.littlethings.base.lib.LibMisc;
import com.gamingsmod.littlethings.base.loader.SectionLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItemFood extends ItemFood implements ILittleThingsItem
{
    public ModItemFood(int amount, float saturation, boolean isWolfFood)
    {
        super(amount, saturation, isWolfFood);
    }

    public ModItemFood(int amount, boolean isWolfFood)
    {
        super(amount, isWolfFood);
    }

    @Override
    public Item setUnlocalizedName(String name)
    {
        super.setUnlocalizedName(name);
        setRegistryName(LibMisc.PREFIX_MOD + name);

        SectionLoader.items.add(this);

        GameRegistry.register(this);

        return this;
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("item.%s%s", LibMisc.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        return getUnlocalizedName();
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }

    @Override
    public void registerItemVariants()
    {
    }

    @Override
    public void registerRender()
    {
        Minecraft.getMinecraft().getRenderItem()
                .getItemModelMesher().register(this, 0, new ModelResourceLocation(LibMisc.PREFIX_MOD + this.getUnlocalizedName().substring(6 + LibMisc.MOD_ID.length()), "inventory"));
    }

    @Override
    public void registerItemModel(int meta, String name)
    {
        ModItem.registerItemModel(this, meta, name);
    }
}
