package com.github.jarva.allthearcanistgear.setup.config;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public class ServerConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static ModConfigSpec SPEC;
    public static ArmorSetConfig ALLTHEMODIUM_CONFIG;
    public static ArmorSetConfig VIBRANIUM_CONFIG;
    public static ArmorSetConfig UNOBTAINIUM_CONFIG;

    public static ArmorSetConfig.SpellbookConfig CREATIVE_SPELLBOOK_CONFIG;

    static {
        BUILDER.push("armor_config");
        BUILDER.define("_comment", "A world restart is needed after changing these config options.");

        ALLTHEMODIUM_CONFIG = ArmorSetConfig.build(
            BUILDER,
            "allthemodium",
            new ArmorSetConfig.DefenseValues(4, 9, 7, 4, 4, 0),
            new ArmorSetConfig.ArcanistStats(200, 6, 6),
            new ArmorSetConfig.Capabilities(true, true, true, false, false, false, true),
            new ArmorSetConfig.BookStats(2, 1.1f)
        );

        VIBRANIUM_CONFIG = ArmorSetConfig.build(
            BUILDER,
            "vibranium",
            new ArmorSetConfig.DefenseValues(6, 11, 9, 6, 5, 0),
            new ArmorSetConfig.ArcanistStats(325, 9, 9),
            new ArmorSetConfig.Capabilities(true, true, true, false, true, false, true),
            new ArmorSetConfig.BookStats(5, 1.2f)
        );

        UNOBTAINIUM_CONFIG = ArmorSetConfig.build(
            BUILDER,
            "unobtainium",
            new ArmorSetConfig.DefenseValues(8, 13, 11, 8, 6, 0),
            new ArmorSetConfig.ArcanistStats(450, 12, 12),
            new ArmorSetConfig.Capabilities(true, true, true, true, true, true, true),
            new ArmorSetConfig.BookStats(10, 1.3f)
        );

        CREATIVE_SPELLBOOK_CONFIG = ArmorSetConfig.SpellbookConfig.build(
                BUILDER,
                "creative",
                "unobtainium",
                new ArmorSetConfig.BookStats(10, 1.3f)
        );

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
