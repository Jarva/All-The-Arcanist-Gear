package com.github.jarva.allthearcanistgear.setup.registry;

import com.github.jarva.allthearcanistgear.common.armor.ArcanistArmorSet;
import com.hollingsworth.arsnouveau.api.perk.PerkSlot;
import com.hollingsworth.arsnouveau.api.registry.PerkRegistry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;

import java.util.Arrays;
import java.util.List;

public class ArsNouveauRegistry {
    public static void postInit() {
        addPerkSlots();
    }

    private static void addPerkSlots() {
        ArcanistArmorSet[] arcanistArmorSets = {AddonItemRegistry.ALLTHEMODIUM, AddonItemRegistry.VIBRANIUM, AddonItemRegistry.UNOBTAINIUM};
        List<PerkSlot> t5 = Arrays.asList(PerkSlot.ONE, PerkSlot.TWO, PerkSlot.THREE);
        List<PerkSlot> t6 = Arrays.asList(PerkSlot.TWO, PerkSlot.THREE, PerkSlot.THREE);
        List<PerkSlot> t7 = Arrays.asList(PerkSlot.THREE, PerkSlot.THREE, PerkSlot.THREE);
        List<PerkSlot> empty = List.of();
        for (ArcanistArmorSet arcanistArmorSet : arcanistArmorSets) {
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                Item item = arcanistArmorSet.getArmorFromSlot(slot);
                if (item == null) continue;
                PerkRegistry.registerPerkProvider(item, List.of(empty, empty, empty, empty, t5, t6, t7));
            }
        }
    }
}
