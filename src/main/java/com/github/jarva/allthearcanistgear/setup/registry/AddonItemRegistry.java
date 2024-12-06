package com.github.jarva.allthearcanistgear.setup.registry;

import com.github.jarva.allthearcanistgear.common.items.armor.ArcanistArmorSet;
import com.github.jarva.allthearcanistgear.common.items.book.AddonSpellBook;
import com.github.jarva.allthearcanistgear.setup.config.ArmorSetConfig;
import com.github.jarva.allthearcanistgear.setup.config.ServerConfig;
import com.hollingsworth.arsnouveau.api.spell.SpellTier;
import com.hollingsworth.arsnouveau.setup.registry.ItemRegistryWrapper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static com.github.jarva.allthearcanistgear.AllTheArcanistGear.MODID;

public class AddonItemRegistry {
    public static final List<ArcanistArmorSet> ARMOR_SETS = new ArrayList<>();
    public static final List<ItemRegistryWrapper<? extends Item>> REGISTERED_ITEMS = new ArrayList<>();
    public static final List<ItemRegistryWrapper<? extends Item>> DATAGEN_ITEMS = new ArrayList<>();
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, MODID);

    public static final ArcanistArmorSet ALLTHEMODIUM = registerArmorSet(ServerConfig.ALLTHEMODIUM_CONFIG, 4, ArsNouveauRegistry.FOUR);
    public static final ArcanistArmorSet VIBRANIUM = registerArmorSet(ServerConfig.VIBRANIUM_CONFIG, 5, ArsNouveauRegistry.FIVE);
    public static final ArcanistArmorSet UNOBTAINIUM = registerArmorSet(ServerConfig.UNOBTAINIUM_CONFIG, 6, ArsNouveauRegistry.SIX);
    public static final ItemRegistryWrapper<AddonSpellBook> CREATIVE = register("creative_spell_book", () -> new AddonSpellBook(UNOBTAINIUM.getConfig(), SpellTier.CREATIVE, 99), false);

    public static ArcanistArmorSet registerArmorSet(ArmorSetConfig config, int tier, SpellTier spellTier) {
        ArcanistArmorSet arcanistArmorSet = new ArcanistArmorSet(config, tier, spellTier);
        ARMOR_SETS.add(arcanistArmorSet);
        return arcanistArmorSet;
    }

    public static <T extends Item> ItemRegistryWrapper<T> register(String name, Supplier<T> item) {
        return register(name, item, true);
    }

    public static <T extends Item> ItemRegistryWrapper<T> register(String name, Supplier<T> item, boolean dataGen) {
        ItemRegistryWrapper<T> registered = new ItemRegistryWrapper<>(ITEMS.register(name, item));
        REGISTERED_ITEMS.add(registered);
        if (dataGen) DATAGEN_ITEMS.add(registered);
        return registered;
    }
}
