package com.gamingsmod.littlethings.base.item;

import com.gamingsmod.littlethings.base.lib.LibMisc;
import com.gamingsmod.littlethings.base.loader.SectionLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItem extends Item implements ILittleThingsItem
{
    protected String baseName;

    public static void registerItemModel(Item item, int meta, String name)
    {
        Minecraft.getMinecraft().getRenderItem()
                .getItemModelMesher().register(item, meta, new ModelResourceLocation(LibMisc.PREFIX_MOD + name, "inventory"));
    }

    @Override
    public Item setUnlocalizedName(String name)
    {
        super.setUnlocalizedName(name);
        baseName = name;
        setRegistryName(LibMisc.PREFIX_MOD + name);

        SectionLoader.items.add(this);

        GameRegistry.register(this);

        return this;
    }

    @Override
    public ModItem setCreativeTab(CreativeTabs tab)
    {
        super.setCreativeTab(tab);
        return this;
    }

    @SideOnly(Side.CLIENT)
    public void registerItemVariants()
    {

    }

    @SideOnly(Side.CLIENT)
    public void registerRender()
    {
        Minecraft.getMinecraft().getRenderItem()
                .getItemModelMesher().register(this, 0, new ModelResourceLocation(LibMisc.PREFIX_MOD + this.getUnlocalizedName().substring(6 + LibMisc.MOD_ID.length()), "inventory"));
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
    public void registerItemModel(int meta, String name)
    {
        registerItemModel(this, meta, name);
    }
}
