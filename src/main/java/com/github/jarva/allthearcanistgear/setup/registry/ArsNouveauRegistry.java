package com.github.jarva.allthearcanistgear.setup.registry;

import com.github.jarva.allthearcanistgear.AllTheArcanistGear;
import com.github.jarva.allthearcanistgear.common.items.armor.ArcanistArmorSet;
import com.github.jarva.allthearcanistgear.common.items.perks.FlightPerk;
import com.github.jarva.allthearcanistgear.common.items.perks.SpectralSightPerk;
import com.github.jarva.allthearcanistgear.common.items.perks.TruesightPerk;
import com.github.jarva.allthearcanistgear.common.items.perks.VitalityPerk;
import com.hollingsworth.arsnouveau.api.perk.PerkSlot;
import com.hollingsworth.arsnouveau.api.registry.PerkRegistry;
import com.hollingsworth.arsnouveau.api.registry.SpellCasterRegistry;
import com.hollingsworth.arsnouveau.api.spell.SpellTier;
import com.hollingsworth.arsnouveau.common.items.SpellBook;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;

import java.util.Arrays;
import java.util.List;

public class ArsNouveauRegistry {
    public static SpellTier SPELL_FOUR = SpellTier.createTier(AllTheArcanistGear.prefix("four"), 4);
    public static SpellTier SPELL_FIVE = SpellTier.createTier(AllTheArcanistGear.prefix("five"), 5);
    public static SpellTier SPELL_SIX = SpellTier.createTier(AllTheArcanistGear.prefix("six"), 6);

    public static final PerkSlot PERK_FOUR = new PerkSlot(AllTheArcanistGear.prefix( "four"), 4);
    public static final PerkSlot PERK_FIVE = new PerkSlot(AllTheArcanistGear.prefix( "five"), 5);
    public static final PerkSlot PERK_SIX = new PerkSlot(AllTheArcanistGear.prefix( "six"), 6);

    public static void postInit() {
        addPerkSlots();
        registerSpellCasters();
    }

    public static void init() {
        registerPerks();
    }

    private static void registerSpellCasters() {
        for (ArcanistArmorSet armorSet : AddonItemRegistry.ARMOR_SETS) {
            SpellBook spellBook = armorSet.getSpellbook();
            SpellCasterRegistry.register(spellBook, (stack) -> stack.get(AddonDataComponentRegistry.EXTENDED_GLYPH_CASTER));
        }
        SpellCasterRegistry.register(AddonItemRegistry.CREATIVE.get(), (stack) -> stack.get(AddonDataComponentRegistry.EXTENDED_GLYPH_CASTER));
    }

    private static void addPerkSlots() {
        List<PerkSlot> t5 = Arrays.asList(PerkSlot.TWO, PerkSlot.THREE, PERK_FOUR);
        List<PerkSlot> t6 = Arrays.asList(PerkSlot.THREE, PERK_FOUR, PERK_FIVE);
        List<PerkSlot> t7 = Arrays.asList(PERK_FOUR, PERK_FIVE, PERK_SIX);
        List<PerkSlot> empty = List.of();

        for (ArcanistArmorSet arcanistArmorSet : AddonItemRegistry.ARMOR_SETS) {
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                Item item = arcanistArmorSet.getArmorFromSlot(slot);
                if (item == null) continue;
                PerkRegistry.registerPerkProvider(item, List.of(empty, empty, empty, empty, t5, t6, t7));
            }
        }
    }

    private static void registerPerks() {
        PerkRegistry.registerPerk(FlightPerk.INSTANCE);
        PerkRegistry.registerPerk(VitalityPerk.INSTANCE);
        PerkRegistry.registerPerk(SpectralSightPerk.INSTANCE);
        PerkRegistry.registerPerk(TruesightPerk.INSTANCE);
    }
}
