package com.github.jarva.allthearcanistgear.setup.config;

import com.github.jarva.allthearcanistgear.AllTheArcanistGear;
import com.github.jarva.allthearcanistgear.common.items.armor.AddonArmorItem;
import com.hollingsworth.arsnouveau.api.perk.PerkAttributes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.DoubleValue;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;

public record ArmorSetConfig(
        String name,
        IntValue head,
        IntValue chest,
        IntValue legs,
        IntValue boots,
        IntValue toughness,
        DoubleValue knockback,
        IntValue maxMana,
        DoubleValue manaRegen,
        DoubleValue spellPower,
        BooleanValue preventDrowning,
        BooleanValue preventKinetic,
        BooleanValue preventFire,
        BooleanValue preventDragonsBreath,
        BooleanValue preventWither,
        BooleanValue preventLevitation,
        BooleanValue preventFallDamage,
        IntValue bonusGlyphs
) {
    public int getDefenseBySlot(EquipmentSlot slot) {
        return switch (slot) {
            case HEAD -> head().get();
            case CHEST -> chest().get();
            case LEGS -> legs().get();
            case FEET -> boots().get();
            default -> throw new IllegalStateException("Unexpected value: " + slot);
        };
    }

    public ItemAttributeModifiers buildAttributeMap(AddonArmorItem item) {
        EquipmentSlot slot = item.getEquipmentSlot();

        int defense = getDefenseBySlot(slot);
                    int toughness = toughness().get();
        double knockback = knockback().get();
        double maxMana = maxMana().get();
        double manaRegen = manaRegen().get();
        double spellPower = spellPower().get();

        ResourceLocation modifier = AllTheArcanistGear.prefix("armor." + name() + "." + slot.getName());
        EquipmentSlotGroup group = EquipmentSlotGroup.bySlot(slot);

        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();

        if (defense != 0) {
            builder.add(Attributes.ARMOR, new AttributeModifier(modifier, defense, Operation.ADD_VALUE), group);
        }
        if (toughness != 0) {
            builder.add(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(modifier, toughness, Operation.ADD_VALUE), group);
        }
        if (knockback != 0) {
            builder.add(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(modifier, knockback, Operation.ADD_VALUE), group);
        }
        if (maxMana != 0) {
            builder.add(PerkAttributes.MAX_MANA, new AttributeModifier(modifier, maxMana, Operation.ADD_VALUE), group);
        }
        if (manaRegen != 0) {
            builder.add(PerkAttributes.MANA_REGEN_BONUS, new AttributeModifier(modifier, manaRegen, Operation.ADD_VALUE), group);
        }
        if (spellPower != 0) {
            builder.add(PerkAttributes.SPELL_DAMAGE_BONUS, new AttributeModifier(modifier, spellPower, Operation.ADD_VALUE), group);
        }

        return builder.build();
    }

    public static ArmorSetConfig build(ModConfigSpec.Builder builder, String name, DefenseValues values, ArcanistStats stats, Capabilities capabilities, BookStats book) {
        String localizedName = name.substring(0, 1).toUpperCase() + name.substring(1);
        builder.push(name);
        builder.define("_comment", "Config for " + localizedName + " armor");

        builder.push("armor_values");
        IntValue head = builder.worldRestart().comment("Hat").defineInRange("hat", values.head(), 0, Integer.MAX_VALUE);
        IntValue chest = builder.worldRestart().comment("Robes").defineInRange("robes", values.chest(), 0, Integer.MAX_VALUE);
        IntValue legs = builder.worldRestart().comment("Leggings").defineInRange("leggings", values.legs(), 0, Integer.MAX_VALUE);
        IntValue boots = builder.worldRestart().comment("Boots").defineInRange("boots", values.boots(), 0, Integer.MAX_VALUE);
        IntValue toughness = builder.worldRestart().comment("Toughness").defineInRange("toughness", values.toughness(), 0, Integer.MAX_VALUE);
        DoubleValue knockback = builder.worldRestart().comment("Knockback Resistance").defineInRange("knockback_resistance", values.knockbackResistance(), 0D, Integer.MAX_VALUE);
        builder.pop();

        builder.push("arcanist_stats");
        IntValue manaBoost = builder.worldRestart().comment("Max Mana Bonus").defineInRange("mana_boost", stats.manaBoost(), 0, Integer.MAX_VALUE);
        DoubleValue manaRegen = builder.worldRestart().comment("Mana Regen Bonus").defineInRange("mana_regen", stats.manaRegen(), 0D, Integer.MAX_VALUE);
        DoubleValue spellPower = builder.worldRestart().comment("Spell Power Bonus").defineInRange("spell_power", stats.spellPower(), 0D, Integer.MAX_VALUE);
        builder.pop();

        builder.push("capabilities");
        BooleanValue preventDrowning = builder.worldRestart().comment("Should Helmet Prevent Drowning?").define("prevent_drowning", capabilities.preventDrowning());
        BooleanValue preventKinetic = builder.worldRestart().comment("Should Helmet Prevent Kinetic Damage?").define("prevent_kinetic", capabilities.preventKinetic());
        BooleanValue preventFire = builder.worldRestart().comment("Should Chestplate Prevent Fire Damage?").define("prevent_fire", capabilities.preventFire());
        BooleanValue preventDragonsBreath = builder.worldRestart().comment("Should Chestplate Prevent Dragon's Breath Damage?").define("prevent_dragons_breath", capabilities.preventDragonsBreath());
        BooleanValue preventWither = builder.worldRestart().comment("Should Leggings Prevent Wither?").define("prevent_wither", capabilities.preventWither());
        BooleanValue preventLevitation = builder.worldRestart().comment("Should Leggings Prevent Levitation?").define("prevent_levitation", capabilities.preventLevitation());
        BooleanValue preventFallDamage = builder.worldRestart().comment("Should Boots Prevent Fall Damage?").define("prevent_fall_damage", capabilities.preventFallDamage());
        builder.pop();

        builder.push("spell_book");
        IntValue bonusGlyphs = builder.worldRestart().comment("How many bonus glyph slots should the spell book provide?").defineInRange("bonus_glyphs", book.bonusGlyphs(), 0, 990);
        builder.pop();

        ArmorSetConfig config = new ArmorSetConfig(name,
                head, chest, legs, boots, toughness, knockback,
                manaBoost, manaRegen, spellPower,
                preventDrowning, preventKinetic, preventFire, preventDragonsBreath, preventWither, preventLevitation, preventFallDamage,
                bonusGlyphs
        );
        builder.pop();
        return config;
    }

    public record DefenseValues(int head, int chest, int legs, int boots, int toughness, double knockbackResistance) {};
    public record ArcanistStats(int manaBoost, double manaRegen, double spellPower) {};
    public record Capabilities(boolean preventDrowning, boolean preventKinetic, boolean preventFire, boolean preventDragonsBreath, boolean preventWither, boolean preventLevitation, boolean preventFallDamage) {};
    public record BookStats(int bonusGlyphs) {};
}
