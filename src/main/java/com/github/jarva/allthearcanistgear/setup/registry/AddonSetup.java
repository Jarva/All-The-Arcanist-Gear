package com.github.jarva.allthearcanistgear.setup.registry;

import net.neoforged.bus.api.IEventBus;

public class AddonSetup {
    public static void register(IEventBus eventBus) {
        AddonItemRegistry.ITEMS.register(eventBus);
        AddonArmorMaterialRegistry.MATERIALS.register(eventBus);
        AddonCreativeTabRegistry.CREATIVE_MOB_TABS.register(eventBus);
        AddonIngredientTypeRegistry.INGREDIENT_TYPES.register(eventBus);
        AddonDataComponentRegistry.DATA.register(eventBus);
        AddonAttributeRegistry.ATTRIBUTES.register(eventBus);
    }
}
