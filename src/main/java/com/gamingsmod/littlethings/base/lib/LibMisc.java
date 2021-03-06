package com.gamingsmod.littlethings.base.lib;

public class LibMisc
{
    public static final String MOD_ID = "littlethings";
    public static final String MOD_NAME = "Little Things";
    public static final String BUILD = "GRADLE:BUILD";
    public static final String VERSION = "GRADLE:VERSION-" + BUILD;
    public static final String DEPENDENCIES = "required-after:Forge@[12.18.1.2053,);";
    public static final String PREFIX_MOD = MOD_ID.toLowerCase() + ":";
    public static final String MOD_ID_LOWER = MOD_ID.toLowerCase();

    public static final String CLIENT_PROXY = "com.gamingsmod.littlethings.base.proxy.ClientProxy";
    public static final String SERVER_PROXY = "com.gamingsmod.littlethings.base.proxy.CommonProxy";
}
