package com.github.jarva.allthearcanistgear.datagen;

import com.github.jarva.allthearcanistgear.AllTheArcanistGear;
import com.github.jarva.allthearcanistgear.common.items.armor.ArcanistArmorSet;
import com.github.jarva.allthearcanistgear.common.recipes.PerkTierIngredient;
import com.github.jarva.allthearcanistgear.setup.registry.AddonItemRegistry;
import com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

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

    private void smithing(RecipeOutput output, ArcanistArmorSet set, TagKey<Item> template, TagKey<Item> modifier) {
        ICondition elementalLoaded = modLoaded("ars_elemental");

        RecipeOutput withElemental = output.withConditions(elementalLoaded);
        smithing(withElemental, set.getHat(), template, ItemTagDatagen.ELEMENTAL_HAT, modifier, 0);
        smithing(withElemental, set.getChest(), template, ItemTagDatagen.ELEMENTAL_CHEST, modifier, 0);
        smithing(withElemental, set.getLegs(), template, ItemTagDatagen.ELEMENTAL_LEGS, modifier, 0);
        smithing(withElemental, set.getBoots(), template, ItemTagDatagen.ELEMENTAL_BOOTS, modifier, 0);
        smithing(withElemental, set.getBoots(), template, ItemTagDatagen.SPELLBOOK, modifier, 2);

        RecipeOutput withoutElemental = output.withConditions(not(elementalLoaded));
        smithing(withoutElemental, set.getHat(), template, ItemTagDatagen.BASE_HAT, modifier, 2);
        smithing(withoutElemental, set.getChest(), template, ItemTagDatagen.BASE_CHEST, modifier, 2);
        smithing(withoutElemental, set.getLegs(), template, ItemTagDatagen.BASE_LEGS, modifier, 2);
        smithing(withoutElemental, set.getBoots(), template, ItemTagDatagen.BASE_BOOTS, modifier, 2);

        smithing(output, set.getSpellbook(), template, ItemsRegistry.ARCHMAGE_SPELLBOOK.get(), modifier);
    }

    private Ingredient minArmorTier(TagKey<Item> base, int tier) {
        return tier > 0 ? new PerkTierIngredient(base, tier).toVanilla() : Ingredient.of(base);
    }

    private void smithing(RecipeOutput output, ArcanistArmorSet set, TagKey<Item> template, ArcanistArmorSet base, TagKey<Item> modifier) {
        smithing(output, set.getHat(), template, base.getHat(), modifier);
        smithing(output, set.getChest(), template, base.getChest(), modifier);
        smithing(output, set.getLegs(), template, base.getLegs(), modifier);
        smithing(output, set.getBoots(), template, base.getBoots(), modifier);

        smithing(output, set.getSpellbook(), template, base.getSpellbook(), modifier);
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

    private void smithing(RecipeOutput output, Item armor, TagKey<Item> template, TagKey<Item> base, TagKey<Item> modifier, int tier) {
        smithing(output, armor, template, minArmorTier(base, tier), modifier, base.location().getPath());
    }

    private void smithing(RecipeOutput output, Item item, TagKey<Item> template, Ingredient base, TagKey<Item> modifier, String baseName) {
        SmithingTransformRecipeBuilder.smithing(
                Ingredient.of(template),
                base,
                Ingredient.of(modifier),
                RecipeCategory.COMBAT,
                item
        ).unlocks(AllTheArcanistGear.MODID + ":has_" + modifier.location().getPath() + "_ingot", has(modifier)).save(output, baseName + "_to_" + getItemName(item) + "_smithing");
    }
}
