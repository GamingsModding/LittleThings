package com.gamingsmod.littlethings.base.block;

import com.gamingsmod.littlethings.base.lib.LibMisc;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ModBlockVariants<T extends Enum<T> & IStringSerializable> extends ModBlock implements IMetaBlockName
{
    public static Class tempVariantsEnum;
    public static PropertyEnum tempVariantProperty;
    public static String[] variants;

    public final Class<T> variantsEnum;
    public final PropertyEnum<T> variantProp;

    public ModBlockVariants(Material material, Class<T> variantsEnum)
    {
        this(material, material.getMaterialMapColor(), variantsEnum);
    }

    @SuppressWarnings("unchecked")
    public ModBlockVariants(Material material, MapColor color, Class<T> variantsEnum)
    {
        super(material, color, asVariantArray(variantsEnum));
        this.variantsEnum = variantsEnum;
        this.variantProp = tempVariantProperty;
        setDefaultState(blockState.getBaseState().withProperty(variantProp, variantsEnum.getEnumConstants()[0]));
    }

    private static String[] asVariantArray(Class e)
    {
        tempVariantsEnum = e;
        tempVariantProperty = PropertyEnum.create("type", e);
        Enum[] values = (Enum[]) e.getEnumConstants();
        String[] variants = new String[values.length];

        for (int i = 0; i < values.length; i++)
            variants[i] = values[i].name().toLowerCase();
        return variants;
    }

    @Override
    public String getSpecialName(ItemStack stack)
    {
        return variantsEnum.getEnumConstants()[stack.getMetadata()].getName();
    }

    @Override
    public BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, tempVariantProperty);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(variantProp == null ? tempVariantProperty : variantProp).ordinal();
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        if (meta >= variantsEnum.getEnumConstants().length)
            meta = 0;

        return getDefaultState().withProperty(variantProp, variantsEnum.getEnumConstants()[meta]);
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return getMetaFromState(state);
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(this, 1, getMetaFromState(world.getBlockState(pos)));
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
    {
        for (int i = 0; i < variantsEnum.getEnumConstants().length; i++)
            list.add(new ItemStack(itemIn, 1, i));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerVariantModels()
    {
        ResourceLocation[] locations = new ResourceLocation[variantsEnum.getEnumConstants().length];
        for (int i = 0; i < locations.length; i++)
            locations[i] = new ResourceLocation(LibMisc.MOD_ID_LOWER, baseName + "_" + variantsEnum.getEnumConstants()[i].getName());

        ModelBakery.registerItemVariants(
                Item.getItemFromBlock(this),
                locations
        );
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerRender()
    {
        for (int i = 0; i < variantsEnum.getEnumConstants().length; i++)
            registerItemModel(i, baseName + "_" + variantsEnum.getEnumConstants()[i].getName());
    }

    public static interface EnumBase extends IStringSerializable
    {
        @Override
        public default String getName()
        {
            return ((Enum) this).name().toLowerCase();
        }
    }
}
