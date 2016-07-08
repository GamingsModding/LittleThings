package com.gamingsmod.littlethings.base.helper;

import net.minecraftforge.fml.common.FMLCommonHandler;

public class Console
{
    public static Object log(Object o)
    {
        System.out.println(o);
        return o;
    }

    public static void dump(Object... os)
    {
        log("--- DUMPING ---");
        int i = 0;
        for (Object o : os) {
            log("ITEM " + i + ": " + o);
            i++;
        }
        log("--- END DUMPING ---");
    }

    public static void dieAndDump(Object... os)
    {
        dump(os);
        FMLCommonHandler.instance().exitJava(1, false);
    }
}
