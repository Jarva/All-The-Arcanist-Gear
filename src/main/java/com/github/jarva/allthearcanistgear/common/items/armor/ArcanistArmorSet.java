package com.github.jarva.allthearcanistgear.common.items.armor;

import com.github.jarva.allthearcanistgear.common.items.book.AddonSpellBook;
import com.github.jarva.allthearcanistgear.setup.config.ArmorSetConfig;
import com.github.jarva.allthearcanistgear.setup.registry.AddonItemRegistry;
import com.hollingsworth.arsnouveau.api.spell.SpellTier;
import com.hollingsworth.arsnouveau.common.items.SpellBook;
import com.hollingsworth.arsnouveau.setup.registry.ItemRegistryWrapper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;

public class ArcanistArmorSet {
    private final ItemRegistryWrapper<AddonArmorItem> head;
    private final ItemRegistryWrapper<AddonArmorItem> chest;
    private final ItemRegistryWrapper<AddonArmorItem> legs;
    private final ItemRegistryWrapper<AddonArmorItem> feet;
    private final ItemRegistryWrapper<SpellBook> spellbook;

    private final String name;
    private final int tier;
    private final ArmorSetConfig config;

    public ArcanistArmorSet(ArmorSetConfig config, int tier, SpellTier spellTier) {
        this.config = config;
        this.name = config.name();
        this.tier = tier;
        this.head = AddonItemRegistry.register(name + "_hat", () -> new AddonArmorItem(config, ArmorItem.Type.HELMET, tier));
        this.chest = AddonItemRegistry.register(name + "_robes", () -> new AddonArmorItem(config, ArmorItem.Type.CHESTPLATE, tier));
        this.legs = AddonItemRegistry.register(name + "_leggings", () -> new AddonArmorItem(config, ArmorItem.Type.LEGGINGS, tier));
        this.feet = AddonItemRegistry.register(name + "_boots", () -> new AddonArmorItem(config, ArmorItem.Type.BOOTS, tier));
        this.spellbook = AddonItemRegistry.register(name + "_spell_book", () -> new AddonSpellBook(config.spellbookConfig(), spellTier, tier), false);
    }

    public ArmorSetConfig getConfig() {
        return this.config;
    }

    public String getName() {
        return this.name;
    }

    public int getTier() {
        return this.tier;
    }

    public AddonArmorItem getHat() {
        return head.get();
    }

    public AddonArmorItem getChest() {
        return chest.get();
    }

    public AddonArmorItem getLegs() {
        return legs.get();
    }

    public AddonArmorItem getBoots() {
        return feet.get();
    }

    public SpellBook getSpellbook() {
        return spellbook.get();
    }

    public AddonArmorItem getArmorFromSlot(EquipmentSlot slot) {
        return switch (slot) {
            case CHEST -> getChest();
            case LEGS -> getLegs();
            case FEET -> getBoots();
            case HEAD -> getHat();
            default -> null;
        };
    }
}
