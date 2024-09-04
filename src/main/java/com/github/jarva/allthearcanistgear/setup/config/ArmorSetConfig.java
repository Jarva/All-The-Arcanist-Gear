package com.github.jarva.allthearcanistgear.setup.config;

import com.github.jarva.allthearcanistgear.AllTheArcanistGear;
import com.github.jarva.allthearcanistgear.common.armor.AddonArmorItem;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.hollingsworth.arsnouveau.api.perk.PerkAttributes;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.*;

import java.util.EnumMap;
import java.util.UUID;

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
        ForgeConfigSpec.BooleanValue preventFallDamage
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

    private static final EnumMap<ArmorItem.Type, UUID> ARMOR_MODIFIER_UUID_PER_TYPE = Util.make(new EnumMap<>(ArmorItem.Type.class), (enumMap) -> {
        enumMap.put(ArmorItem.Type.BOOTS, UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"));
        enumMap.put(ArmorItem.Type.LEGGINGS, UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"));
        enumMap.put(ArmorItem.Type.CHESTPLATE, UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"));
        enumMap.put(ArmorItem.Type.HELMET, UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150"));
    });

    public Multimap<Attribute, AttributeModifier> buildAttributeMap(AddonArmorItem item) {
        EquipmentSlot slot = item.getEquipmentSlot();

        int defense = getDefenseBySlot(slot);
                    int toughness = toughness().get();
        double knockback = knockback().get();
        double maxMana = maxMana().get();
        double manaRegen = manaRegen().get();
        double spellPower = spellPower().get();

        ResourceLocation modifier = AllTheArcanistGear.prefix("armor." + name() + "." + slot.getName());

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();

        UUID uuid = ARMOR_MODIFIER_UUID_PER_TYPE.get(item.getEquipmentSlot());

        if (defense != 0) {
            builder.put(Attributes.ARMOR, new AttributeModifier(uuid, modifier.toString(), defense, Operation.ADDITION));
        }
        if (toughness != 0) {
            builder.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, modifier.toString(), toughness, Operation.ADDITION));
        }
        if (knockback != 0) {
            builder.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, modifier.toString(), knockback, Operation.ADDITION));
        }
        if (maxMana != 0) {
            builder.put(PerkAttributes.MAX_MANA.get(), new AttributeModifier(uuid, modifier.toString(), maxMana, Operation.ADDITION));
        }
        if (manaRegen != 0) {
            builder.put(PerkAttributes.MANA_REGEN_BONUS.get(), new AttributeModifier(uuid, modifier.toString(), manaRegen, Operation.ADDITION));
        }
        if (spellPower != 0) {
            builder.put(PerkAttributes.SPELL_DAMAGE_BONUS.get(), new AttributeModifier(uuid, modifier.toString(), spellPower, Operation.ADDITION));
        }

        return builder.build();
    }

    public static ArmorSetConfig build(ForgeConfigSpec.Builder builder, String name, DefenseValues values, ArcanistStats stats, Capabilities capabilities) {
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

        ArmorSetConfig config = new ArmorSetConfig(name,
                head, chest, legs, boots, toughness, knockback,
                manaBoost, manaRegen, spellPower,
                preventDrowning, preventKinetic, preventFire, preventDragonsBreath, preventWither, preventLevitation, preventFallDamage
        );
        builder.pop();
        return config;
    }

    public record DefenseValues(int head, int chest, int legs, int boots, int toughness, double knockbackResistance) {};
    public record ArcanistStats(int manaBoost, double manaRegen, double spellPower) {};
    public record Capabilities(boolean preventDrowning, boolean preventKinetic, boolean preventFire, boolean preventDragonsBreath, boolean preventWither, boolean preventLevitation, boolean preventFallDamage) {};
}
