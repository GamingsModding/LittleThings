package com.gamingsmod.littlethings.base.item;

import com.gamingsmod.littlethings.base.lib.LibMisc;
import com.gamingsmod.littlethings.base.loader.SectionLoader;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Set;

public class ModItemTool extends ItemTool implements ILittleThingsItem
{
    public ModItemTool(float damageVsEntity, float attackSpeed, ToolMaterial material, Set<Block> effective)
    {
        super(damageVsEntity, attackSpeed, material, effective);
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
