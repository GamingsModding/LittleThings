package com.gamingsmod.littlethings.base.block;


import com.gamingsmod.littlethings.base.item.ItemBlockMeta;
import com.gamingsmod.littlethings.base.lib.LibMisc;
import com.gamingsmod.littlethings.base.loader.SectionLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlockColored extends BlockColored implements ILittleThingsBlock, IMetaBlockName
{
    protected String baseName;

    public ModBlockColored(Material material)
    {
        super(material);
    }

    @Override
    public Block setUnlocalizedName(String name)
    {
        super.setUnlocalizedName(name);
        baseName = name;

        if (getRegistryName() == null)
            setRegistryName(LibMisc.PREFIX_MOD + name);

        SectionLoader.blocks.add(this);

        GameRegistry.register(this);
        GameRegistry.register(new ItemBlockMeta(this), getRegistryName());

        return this;
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("tile.%s%s", LibMisc.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerVariantModels()
    {
        ResourceLocation[] locations = new ResourceLocation[EnumDyeColor.values().length];
        for (int i = 0; i < locations.length; i++)
            locations[i] = new ResourceLocation(LibMisc.MOD_ID_LOWER, baseName + "_" + EnumDyeColor.values()[i].getName());

        ModelBakery.registerItemVariants(
                Item.getItemFromBlock(this),
                locations
        );
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerRender()
    {
        for (int i = 0; i < EnumDyeColor.values().length; i++)
            registerItemModel(i, baseName + "_" + EnumDyeColor.values()[i].getName());
    }


    public void registerItemModel(int meta, String file)
    {
        Minecraft.getMinecraft().getRenderItem()
                .getItemModelMesher().register(Item.getItemFromBlock(this), meta, new ModelResourceLocation(LibMisc.PREFIX_MOD + file, "inventory"));
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }

    @Override
    public String getSpecialName(ItemStack stack)
    {
        return EnumDyeColor.values()[stack.getMetadata()].getName();
    }
}
