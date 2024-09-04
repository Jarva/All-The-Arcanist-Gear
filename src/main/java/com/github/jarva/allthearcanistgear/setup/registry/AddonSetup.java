package com.github.jarva.allthearcanistgear.setup.registry;

import com.github.jarva.allthearcanistgear.AllTheArcanistGear;
import com.github.jarva.allthearcanistgear.common.recipes.PerkTierIngredient;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.eventbus.api.IEventBus;

public class AddonSetup {
    public static void register(IEventBus eventBus) {
        AddonItemRegistry.ITEMS.register(eventBus);
//        AddonArmorMaterialRegistry.MATERIALS.register(eventBus);
        AddonCreativeTabRegistry.CREATIVE_MOB_TABS.register(eventBus);
    }

    public static void registerIngredients() {
        CraftingHelper.register(AllTheArcanistGear.prefix("perk_tier"), PerkTierIngredient.INSTANCE);
    }
}
