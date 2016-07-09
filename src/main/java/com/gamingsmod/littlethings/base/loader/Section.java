package com.gamingsmod.littlethings.base.loader;

import com.gamingsmod.littlethings.base.helper.ConfigHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.gamingsmod.littlethings.base.loader.SectionLoader.createHumanClassNames;

public class Section
{
    private HashMap<String, Addition> additions = new HashMap<>();
    private ArrayList<Addition> enabled = new ArrayList<>();

    public void registerAdditions()
    {

    }

    protected void addAddition(Addition addition)
    {
        String name = createHumanClassNames(addition.getClass()).replace(" Section", "");
        addAddition(addition, name);
    }

    protected void addAddition(Addition addition, String name)
    {
        if (!additions.containsKey(name))
            additions.put(name, addition);
        else
            throw new RuntimeException(name + " already exists in " + this.getClass().getName());

    }

    public void preInit(FMLPreInitializationEvent e)
    {
        forEachEnabled(addition -> addition.preInit(e));
    }

    public void init(FMLInitializationEvent e)
    {
        forEachEnabled(addition -> {
            addition.init(e);
            if (addition.hasSubscriptions())
                MinecraftForge.EVENT_BUS.register(addition);
        });
    }

    public void postInit(FMLPostInitializationEvent e)
    {
        forEachEnabled(addition -> addition.postInit(e));
    }

    public void clientPreInit(FMLPreInitializationEvent e)
    {
        forEachEnabled(addition -> addition.clientPreInit(e));
    }

    public void clientInit(FMLInitializationEvent e)
    {
        forEachEnabled(addition -> addition.clientInit(e));
    }

    public void clientPostInit(FMLPostInitializationEvent e)
    {
        forEachEnabled(addition -> addition.clientPostInit(e));
    }

    public void serverStarting(FMLServerStartingEvent e)
    {
        forEachEnabled(addition -> addition.serverStarting(e));
    }

    public void setupConfig()
    {
        if (additions.isEmpty())
            registerAdditions();

        forEachAddition((name, addition) -> {
            boolean enabled = ConfigHelper.getBool(createHumanClassNames(this.getClass()), "Enable " + name, addition.enabledByDefault());
            SectionLoader.additionInstances.put(addition.getClass(), enabled);
            if (enabled) this.enabled.add(addition);

            addition.category = name;
        });

        forEachEnabled(Addition::setupConfig);
    }

    public boolean enabledByDefault()
    {
        return true;
    }

    @SuppressWarnings("unchecked")
    private void forEachAddition(BiConsumer<String, Addition> run)
    {
        additions.forEach(run);
    }

    private void forEachEnabled(Consumer<Addition> run)
    {
        enabled.forEach(run);
    }
}
