package com.gamingsmod.littlethings.base.block;

import com.gamingsmod.littlethings.base.item.ItemBlockMeta;
import com.gamingsmod.littlethings.base.lib.LibMisc;
import com.gamingsmod.littlethings.base.loader.SectionLoader;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlock extends Block implements ILittleThingsBlock
{
    public String[] variants;
    protected String baseName;

    public ModBlock(Material material)
    {
        super(material);
    }

    public ModBlock(Material material, MapColor color)
    {
        super(material, color);
    }

    protected ModBlock(Material material, MapColor color, String... variants)
    {
        this(material, color);
        this.variants = variants;
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("tile.%s%s", LibMisc.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    public ModBlock setUnlocalizedName(String name)
    {
        super.setUnlocalizedName(name);
        baseName = name;
        if (getRegistryName() == null)
            setRegistryName(LibMisc.PREFIX_MOD + name);

        SectionLoader.blocks.add(this);

        GameRegistry.register(this);

        if (this instanceof IMetaBlockName)
            GameRegistry.register(new ItemBlockMeta(this), getRegistryName());
        else
            GameRegistry.register(new ItemBlock(this), getRegistryName());

        return this;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerRender()
    {
        Minecraft.getMinecraft().getRenderItem()
                .getItemModelMesher().register(Item.getItemFromBlock(this), 0, new ModelResourceLocation(LibMisc.PREFIX_MOD + this.getUnlocalizedName().substring(6 + LibMisc.MOD_ID.length()), "inventory"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerVariantModels()
    {

    }

    @Override
    public void registerItemModel(int meta, String file)
    {
        Minecraft.getMinecraft().getRenderItem()
                .getItemModelMesher().register(Item.getItemFromBlock(this), meta, new ModelResourceLocation(LibMisc.PREFIX_MOD + file, "inventory"));
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }
}
