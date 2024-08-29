package com.github.jarva.allthearcanistgear.setup.registry;

import com.github.jarva.allthearcanistgear.AllTheArcanistGear;
import com.github.jarva.allthearcanistgear.common.recipes.PerkTierIngredient;
import net.neoforged.neoforge.common.crafting.IngredientType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class AddonIngredientTypeRegistry {
    public static final DeferredRegister<IngredientType<?>> INGREDIENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.INGREDIENT_TYPES, AllTheArcanistGear.MODID);

    public static final Supplier<IngredientType<PerkTierIngredient>> PERK_TIER = INGREDIENT_TYPES.register("perk_tier",
            () -> new IngredientType<PerkTierIngredient>(PerkTierIngredient.CODEC, PerkTierIngredient.STREAM_CODEC)
    );
}
