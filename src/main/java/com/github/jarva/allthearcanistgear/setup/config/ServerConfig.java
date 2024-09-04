package com.github.jarva.allthearcanistgear.setup.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ServerConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec SPEC;
    public static ArmorSetConfig ALLTHEMODIUM_CONFIG;
    public static ArmorSetConfig VIBRANIUM_CONFIG;
    public static ArmorSetConfig UNOBTAINIUM_CONFIG;

    static {
        BUILDER.push("armor_config");
        BUILDER.define("_comment", "A world restart is needed after changing these config options.");

        ALLTHEMODIUM_CONFIG = ArmorSetConfig.build(
            BUILDER,
            "allthemodium",
            new ArmorSetConfig.DefenseValues(4, 9, 7, 4, 4, 0),
            new ArmorSetConfig.ArcanistStats(200, 6, 6),
            new ArmorSetConfig.Capabilities(true, true, true, false, false, false, true)
        );

        VIBRANIUM_CONFIG = ArmorSetConfig.build(
            BUILDER,
            "vibranium",
            new ArmorSetConfig.DefenseValues(6, 11, 9, 6, 5, 0),
            new ArmorSetConfig.ArcanistStats(325, 9, 9),
            new ArmorSetConfig.Capabilities(true, true, true, false, true, false, true)
        );

        UNOBTAINIUM_CONFIG = ArmorSetConfig.build(
            BUILDER,
            "unobtainium",
            new ArmorSetConfig.DefenseValues(8, 13, 11, 8, 6, 0),
            new ArmorSetConfig.ArcanistStats(450, 12, 12),
            new ArmorSetConfig.Capabilities(true, true, true, true, true, true, true)
        );

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
