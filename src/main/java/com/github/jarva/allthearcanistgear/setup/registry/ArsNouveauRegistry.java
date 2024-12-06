package com.github.jarva.allthearcanistgear.setup.registry;

import com.github.jarva.allthearcanistgear.AllTheArcanistGear;
import com.github.jarva.allthearcanistgear.common.items.armor.ArcanistArmorSet;
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
    public static SpellTier FOUR = SpellTier.createTier(AllTheArcanistGear.prefix("four"), 4);
    public static SpellTier FIVE = SpellTier.createTier(AllTheArcanistGear.prefix("five"), 5);
    public static SpellTier SIX = SpellTier.createTier(AllTheArcanistGear.prefix("six"), 6);

    public static void postInit() {
        addPerkSlots();
        registerSpellCasters();
    }

    private static void registerSpellCasters() {
        for (ArcanistArmorSet armorSet : AddonItemRegistry.ARMOR_SETS) {
            SpellBook spellBook = armorSet.getSpellbook();
            SpellCasterRegistry.register(spellBook, (stack) -> stack.get(AddonDataComponentRegistry.EXTENDED_GLYPH_CASTER));
        }
        SpellCasterRegistry.register(AddonItemRegistry.CREATIVE.get(), (stack) -> stack.get(AddonDataComponentRegistry.EXTENDED_GLYPH_CASTER));
    }

    private static void addPerkSlots() {
        List<PerkSlot> t5 = Arrays.asList(PerkSlot.ONE, PerkSlot.TWO, PerkSlot.THREE);
        List<PerkSlot> t6 = Arrays.asList(PerkSlot.TWO, PerkSlot.THREE, PerkSlot.THREE);
        List<PerkSlot> t7 = Arrays.asList(PerkSlot.THREE, PerkSlot.THREE, PerkSlot.THREE);
        List<PerkSlot> empty = List.of();

        for (ArcanistArmorSet arcanistArmorSet : AddonItemRegistry.ARMOR_SETS) {
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                Item item = arcanistArmorSet.getArmorFromSlot(slot);
                if (item == null) continue;
                PerkRegistry.registerPerkProvider(item, List.of(empty, empty, empty, empty, t5, t6, t7));
            }
        }
    }
}
