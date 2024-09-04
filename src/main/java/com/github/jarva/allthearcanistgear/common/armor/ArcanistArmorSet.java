package com.github.jarva.allthearcanistgear.common.armor;

import com.github.jarva.allthearcanistgear.setup.config.ArmorSetConfig;
import com.github.jarva.allthearcanistgear.setup.registry.AddonItemRegistry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public class ArcanistArmorSet {
    private final RegistryObject<Item> head;
    private final RegistryObject<Item> chest;
    private final RegistryObject<Item> legs;
    private final RegistryObject<Item> feet;
    private final String name;
    private final int tier;
    private final ArmorSetConfig config;

    public ArcanistArmorSet(ArmorSetConfig config, int tier) {
        this.config = config;
        this.name = config.name();
        this.tier = tier;
        this.head = AddonItemRegistry.register(name + "_hat", () -> new AddonArmorItem(config, ArmorItem.Type.HELMET, tier));
        this.chest = AddonItemRegistry.register(name + "_robes", () -> new AddonArmorItem(config, ArmorItem.Type.CHESTPLATE, tier));
        this.legs = AddonItemRegistry.register(name + "_leggings", () -> new AddonArmorItem(config, ArmorItem.Type.LEGGINGS, tier));
        this.feet = AddonItemRegistry.register(name + "_boots", () -> new AddonArmorItem(config, ArmorItem.Type.BOOTS, tier));
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

    public Item getHat() {
        return head.get();
    }

    public Item getChest() {
        return chest.get();
    }

    public Item getLegs() {
        return legs.get();
    }

    public Item getBoots() {
        return feet.get();
    }

    public Item getArmorFromSlot(EquipmentSlot slot) {
        return switch (slot) {
            case CHEST -> getChest();
            case LEGS -> getLegs();
            case FEET -> getBoots();
            case HEAD -> getHat();
            default -> null;
        };
    }
}
