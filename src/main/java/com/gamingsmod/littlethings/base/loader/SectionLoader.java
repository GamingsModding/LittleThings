package com.gamingsmod.littlethings.base.loader;

import com.gamingsmod.littlethings.automation.AutomationSection;
import com.gamingsmod.littlethings.base.block.ILittleThingsBlock;
import com.gamingsmod.littlethings.base.helper.ConfigHelper;
import com.gamingsmod.littlethings.base.helper.LogHelper;
import com.gamingsmod.littlethings.base.item.ILittleThingsItem;
import com.gamingsmod.littlethings.building.BuildingSection;
import com.gamingsmod.littlethings.decoration.DecorationSection;
import com.gamingsmod.littlethings.vanity.VanitySection;
import com.gamingsmod.littlethings.weapons.WeaponsSection;
import com.gamingsmod.littlethings.world.WorldSection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class SectionLoader
{
    private static final String CATEGORY_SECTIONS = "_sections";
    public static HashMap<Class<? extends Addition>, Boolean> additionInstances = new HashMap<>();
    public static List<ILittleThingsBlock> blocks = new ArrayList<>();
    public static List<ILittleThingsItem> items = new ArrayList<>();
    public static Configuration config;
    private static List<Class<? extends Section>> sections;
    private static HashMap<Class<? extends Section>, Section> sectionInstances = new HashMap<>();

    static {
        sections = new ArrayList<>();

        registerSection(WorldSection.class);
        registerSection(AutomationSection.class);
        registerSection(DecorationSection.class);
        registerSection(VanitySection.class);
        registerSection(WeaponsSection.class);
        registerSection(BuildingSection.class);
    }

    public static void setupConfig(FMLPreInitializationEvent e)
    {
        File suggested = e.getSuggestedConfigurationFile();
        config = new Configuration(suggested);
        config.load();

        List<Section> sections = new ArrayList<>(sectionInstances.values());

        sections.forEach(section -> {
            boolean enabled = ConfigHelper.getBool(CATEGORY_SECTIONS, createHumanClassNames(section.getClass()).replace(" Section", ""), section.enabledByDefault());
            if (!enabled)
                sectionInstances.remove(section.getClass());
        });

        forEachSection(Section::setupConfig);
        if (config.hasChanged())
            config.save();
    }

    public static void preInit(FMLPreInitializationEvent e)
    {
        sections.forEach(aClass -> {
            try {
                sectionInstances.put(aClass, aClass.newInstance());
            } catch (Exception exception) {
                throw new RuntimeException("Can't create instance of section: " + aClass.getName(), exception);
            }
        });

        setupConfig(e);

        forEachSection(section -> {
            LogHelper.info("Loading " + createHumanClassNames(section.getClass()) + "...");
        });

        forEachSection(section -> section.preInit(e));
    }

    public static void init(FMLInitializationEvent e)
    {
        forEachSection(section -> section.init(e));
    }

    public static void postInit(FMLPostInitializationEvent e)
    {
        forEachSection(section -> section.postInit(e));
    }

    public static void clientPreInit(FMLPreInitializationEvent e)
    {
        blocks.forEach(ILittleThingsBlock::registerVariantModels);
        items.forEach(ILittleThingsItem::registerItemVariants);
        forEachSection(section -> section.clientPreInit(e));
    }

    public static void clientInit(FMLInitializationEvent e)
    {
        blocks.forEach(ILittleThingsBlock::registerRender);
        items.forEach((item) -> {
            ItemMeshDefinition def = item.registerCustomMeshDefinition();

            if (def == null) item.registerRender();
            else Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register((Item) item, def);
        });
        forEachSection(section -> section.clientInit(e));
    }

    public static void clientPostInit(FMLPostInitializationEvent e)
    {
        forEachSection(section -> section.clientPostInit(e));
    }

    public static void serverStarting(FMLServerStartingEvent e)
    {
        forEachSection(section -> section.serverStarting(e));
    }

    private static void registerSection(Class<? extends Section> section)
    {
        if (!sections.contains(section))
            sections.add(section);
    }

    private static void forEachSection(Consumer<Section> consumer)
    {
        sectionInstances.values().forEach(consumer);
    }

    public static String createHumanClassNames(Class<?> clazz)
    {
//        return clazz.getSimpleName().replaceAll(
//                String.format("%s|%s|%s",
//                        "(?<=[A-Z])(?=[A-Z][a-z])",
//                        "(?<=[^A-Z])(?=[A-Z])",
//                        "(?<=[A-Za-z])(?=[^A-Za-z])"
//                ),
//                " "
//        );

        return StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(clazz.getSimpleName()), " ");
    }

    public static boolean isAdditionEnabled(Class<? extends Addition> clazz)
    {
        return additionInstances.get(clazz);
    }
}
