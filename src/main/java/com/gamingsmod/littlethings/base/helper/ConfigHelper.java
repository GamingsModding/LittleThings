package com.gamingsmod.littlethings.base.helper;

import com.gamingsmod.littlethings.base.loader.SectionLoader;

public class ConfigHelper
{
    public static boolean getBool(String cat, String name, boolean _default)
    {
        return getBool(cat, name, _default, "");
    }

    public static boolean getBool(String cat, String name, boolean _default, String comment)
    {
        return SectionLoader.config.getBoolean(name, cat, _default, comment);
    }

    public static int getInt(String cat, String name, int _default, int min, int max)
    {
        return getInt(cat, name, _default, min, max, "");
    }

    public static int getInt(String cat, String name, int _default, int min, int max, String comment)
    {
        return SectionLoader.config.getInt(name, cat, _default, min, max, comment);
    }

    public static float getFloat(String cat, String name, float _default, float min, float max)
    {
        return getFloat(cat, name, _default, min, max, "");
    }

    public static float getFloat(String cat, String name, float _default, float min, float max, String comment)
    {
        return SectionLoader.config.getFloat(name, cat, _default, min, max, comment);
    }
}
