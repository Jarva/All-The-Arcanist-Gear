package com.github.jarva.allthearcanistgear.datagen;

import com.github.jarva.allthearcanistgear.AllTheArcanistGear;
import com.github.jarva.allthearcanistgear.common.armor.ArmorSet;
import com.github.jarva.allthearcanistgear.common.recipes.PerkTierIngredient;
import com.github.jarva.allthearcanistgear.setup.registry.AddonItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.neoforged.neoforge.common.crafting.ICustomIngredient;

import java.util.concurrent.CompletableFuture;

public class RecipeDatagen extends RecipeProvider implements IConditionBuilder {
    public RecipeDatagen(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(packOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        RecipeOutput withATM = output.withConditions(modLoaded("allthemodium"));
        smithing(withATM, AddonItemRegistry.ALLTHEMODIUM, ItemTagDatagen.ALLTHEMODIUM_SMITHING_TEMPLATE, ItemTagDatagen.ALLTHEMODIUM_INGOT);
        smithing(withATM, AddonItemRegistry.VIBRANIUM, ItemTagDatagen.VIBRANIUM_SMITHING_TEMPLATE, AddonItemRegistry.ALLTHEMODIUM, ItemTagDatagen.VIBRANIUM_INGOT);
        smithing(withATM, AddonItemRegistry.UNOBTAINIUM, ItemTagDatagen.UNOBTAINIUM_SMITHING_TEMPLATE, AddonItemRegistry.VIBRANIUM, ItemTagDatagen.UNOBTAINIUM_INGOT);
    }

    private void smithing(RecipeOutput output, ArmorSet set, TagKey<Item> template, TagKey<Item> modifier) {
        ICondition elementalLoaded = modLoaded("ars_elemental");

        RecipeOutput withElemental = output.withConditions(elementalLoaded);
        smithing(withElemental, set.getHat(), template, ItemTagDatagen.ELEMENTAL_HAT, modifier);
        smithing(withElemental, set.getChest(), template, ItemTagDatagen.ELEMENTAL_CHEST, modifier);
        smithing(withElemental, set.getLegs(), template, ItemTagDatagen.ELEMENTAL_LEGS, modifier);
        smithing(withElemental, set.getBoots(), template, ItemTagDatagen.ELEMENTAL_BOOTS, modifier);

        RecipeOutput withoutElemental = output.withConditions(not(elementalLoaded));
        smithing(withoutElemental, set.getHat(), template, ItemTagDatagen.BASE_HAT, modifier);
        smithing(withoutElemental, set.getChest(), template, ItemTagDatagen.BASE_CHEST, modifier);
        smithing(withoutElemental, set.getLegs(), template, ItemTagDatagen.BASE_LEGS, modifier);
        smithing(withoutElemental, set.getBoots(), template, ItemTagDatagen.BASE_BOOTS, modifier);
    }

    private void smithing(RecipeOutput output, ArmorSet set, TagKey<Item> template, ArmorSet base, TagKey<Item> modifier) {
        smithing(output, set.getHat(), template, base.getHat(), modifier);
        smithing(output, set.getChest(), template, base.getChest(), modifier);
        smithing(output, set.getLegs(), template, base.getLegs(), modifier);
        smithing(output, set.getBoots(), template, base.getBoots(), modifier);
    }

    private void smithing(RecipeOutput output, Item armor, TagKey<Item> template, Item base, TagKey<Item> modifier) {
        SmithingTransformRecipeBuilder.smithing(
                Ingredient.of(template),
                Ingredient.of(base),
                Ingredient.of(modifier),
                RecipeCategory.COMBAT,
                armor
        ).unlocks(AllTheArcanistGear.MODID + ":has_" + modifier.location().getPath() + "_ingot", has(modifier)).save(output, getItemName(armor) + "_smithing");
    }

    private void smithing(RecipeOutput output, Item armor, TagKey<Item> template, TagKey<Item> base, TagKey<Item> modifier) {
        SmithingTransformRecipeBuilder.smithing(
                Ingredient.of(template),
                new PerkTierIngredient(base, 2).toVanilla(),
                Ingredient.of(modifier),
                RecipeCategory.COMBAT,
                armor
        ).unlocks(AllTheArcanistGear.MODID + ":has_" + modifier.location().getPath() + "_ingot", has(modifier)).save(output, base.location().getPath() + "_to_" + getItemName(armor) + "_smithing");
    }
}
