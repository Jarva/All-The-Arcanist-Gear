package com.github.jarva.allthearcanistgear.setup.registry;

import com.github.jarva.allthearcanistgear.common.armor.ArcanistArmorSet;
import com.github.jarva.allthearcanistgear.setup.config.ArmorSetConfig;
import com.github.jarva.allthearcanistgear.setup.config.ServerConfig;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static com.github.jarva.allthearcanistgear.AllTheArcanistGear.MODID;

public class AddonItemRegistry {
    public static final List<ArcanistArmorSet> ARMOR_SETS = new ArrayList<>();
    public static final List<RegistryObject<? extends Item>> REGISTERED_ITEMS = new ArrayList<>();
    public static final List<RegistryObject<? extends Item>> DATAGEN_ITEMS = new ArrayList<>();
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, MODID);

    public static final ArcanistArmorSet ALLTHEMODIUM = registerArmorSet(ServerConfig.ALLTHEMODIUM_CONFIG, 4);
    public static final ArcanistArmorSet VIBRANIUM = registerArmorSet(ServerConfig.VIBRANIUM_CONFIG, 5);
    public static final ArcanistArmorSet UNOBTAINIUM = registerArmorSet(ServerConfig.UNOBTAINIUM_CONFIG, 6);

    public static ArcanistArmorSet registerArmorSet(ArmorSetConfig config, int tier) {
        ArcanistArmorSet arcanistArmorSet = new ArcanistArmorSet(config, tier);
        ARMOR_SETS.add(arcanistArmorSet);
        return arcanistArmorSet;
    }

    public static RegistryObject<Item> register(String name, Supplier<Item> item) {
        return register(name, item, true);
    }

    public static RegistryObject<Item> register(String name, Supplier<Item> item, boolean dataGen) {
        RegistryObject<Item> registered = ITEMS.register(name, item);
        REGISTERED_ITEMS.add(registered);
        if (dataGen) DATAGEN_ITEMS.add(registered);
        return registered;
    }
}
