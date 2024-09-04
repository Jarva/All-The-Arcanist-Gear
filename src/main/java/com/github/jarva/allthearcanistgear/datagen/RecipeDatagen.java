package com.github.jarva.allthearcanistgear.datagen;

import com.github.jarva.allthearcanistgear.AllTheArcanistGear;
import com.github.jarva.allthearcanistgear.common.armor.ArcanistArmorSet;
import com.github.jarva.allthearcanistgear.common.recipes.PerkTierIngredient;
import com.github.jarva.allthearcanistgear.setup.registry.AddonItemRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class RecipeDatagen extends RecipeProvider implements IConditionBuilder {
    public RecipeDatagen(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> output) {
        ConditionalRecipe.Builder withATM = ConditionalRecipe.builder().addCondition(modLoaded("allthemodium"));
        smithing(output, withATM, AddonItemRegistry.ALLTHEMODIUM, ItemTagDatagen.ALLTHEMODIUM_SMITHING_TEMPLATE, ItemTagDatagen.ALLTHEMODIUM_INGOT);
        smithing(output, withATM, AddonItemRegistry.VIBRANIUM, ItemTagDatagen.VIBRANIUM_SMITHING_TEMPLATE, AddonItemRegistry.ALLTHEMODIUM, ItemTagDatagen.VIBRANIUM_INGOT);
        smithing(output, withATM, AddonItemRegistry.UNOBTAINIUM, ItemTagDatagen.UNOBTAINIUM_SMITHING_TEMPLATE, AddonItemRegistry.VIBRANIUM, ItemTagDatagen.UNOBTAINIUM_INGOT);
    }

    private void smithing(Consumer<FinishedRecipe> consumer, ConditionalRecipe.Builder output, ArcanistArmorSet set, TagKey<Item> template, TagKey<Item> modifier) {
        ICondition elementalLoaded = modLoaded("ars_elemental");

        ConditionalRecipe.Builder withElemental = output.addCondition(elementalLoaded);
        smithing(consumer, withElemental, set.getHat(), template, ItemTagDatagen.ELEMENTAL_HAT, modifier, 0);
        smithing(consumer, withElemental, set.getChest(), template, ItemTagDatagen.ELEMENTAL_CHEST, modifier, 0);
        smithing(consumer, withElemental, set.getLegs(), template, ItemTagDatagen.ELEMENTAL_LEGS, modifier, 0);
        smithing(consumer, withElemental, set.getBoots(), template, ItemTagDatagen.ELEMENTAL_BOOTS, modifier, 0);

        ConditionalRecipe.Builder withoutElemental = output.addCondition(not(elementalLoaded));
        smithing(consumer, withoutElemental, set.getHat(), template, ItemTagDatagen.BASE_HAT, modifier, 3);
        smithing(consumer, withoutElemental, set.getChest(), template, ItemTagDatagen.BASE_CHEST, modifier, 3);
        smithing(consumer, withoutElemental, set.getLegs(), template, ItemTagDatagen.BASE_LEGS, modifier, 3);
        smithing(consumer, withoutElemental, set.getBoots(), template, ItemTagDatagen.BASE_BOOTS, modifier, 3);
    }

    private Ingredient minTier(TagKey<Item> base, int tier) {
        return tier > 0 ? new PerkTierIngredient(base, tier) : Ingredient.of(base);
    }

    private void smithing(Consumer<FinishedRecipe> consumer, ConditionalRecipe.Builder output, ArcanistArmorSet set, TagKey<Item> template, ArcanistArmorSet base, TagKey<Item> modifier) {
        smithing(consumer, output, set.getHat(), template, base.getHat(), modifier);
        smithing(consumer, output, set.getChest(), template, base.getChest(), modifier);
        smithing(consumer, output, set.getLegs(), template, base.getLegs(), modifier);
        smithing(consumer, output, set.getBoots(), template, base.getBoots(), modifier);
    }

    private void smithing(Consumer<FinishedRecipe> consumer, ConditionalRecipe.Builder output, Item armor, TagKey<Item> template, Item base, TagKey<Item> modifier) {
        SmithingTransformRecipeBuilder recipe = SmithingTransformRecipeBuilder.smithing(
                Ingredient.of(template),
                Ingredient.of(base),
                Ingredient.of(modifier),
                RecipeCategory.COMBAT,
                armor
        ).unlocks(AllTheArcanistGear.MODID + ":has_" + modifier.location().getPath() + "_ingot", has(modifier));
        output
            .addRecipe((saver) -> recipe.save(saver, AllTheArcanistGear.prefix(getItemName(armor) + "_smithing")))
            .generateAdvancement()
            .build(consumer, AllTheArcanistGear.prefix(getItemName(armor) + "_smithing"));
    }

    private void smithing(Consumer<FinishedRecipe> consumer, ConditionalRecipe.Builder output, Item armor, TagKey<Item> template, TagKey<Item> base, TagKey<Item> modifier, int tier) {
        smithing(consumer, output, armor, template, minTier(base, tier), modifier, base.location().getPath());
    }

    private void smithing(Consumer<FinishedRecipe> consumer, ConditionalRecipe.Builder output, Item armor, TagKey<Item> template, Ingredient base, TagKey<Item> modifier, String baseName) {
        SmithingTransformRecipeBuilder recipe = SmithingTransformRecipeBuilder.smithing(
                Ingredient.of(template),
                base,
                Ingredient.of(modifier),
                RecipeCategory.COMBAT,
                armor
        ).unlocks(AllTheArcanistGear.MODID + ":has_" + modifier.location().getPath() + "_ingot", has(modifier));
        output
            .addRecipe((saver) -> recipe.save(saver, AllTheArcanistGear.prefix(baseName + "_to_" + getItemName(armor) + "_smithing")))
            .generateAdvancement()
            .build(consumer, AllTheArcanistGear.prefix(baseName + "_to_" + getItemName(armor) + "_smithing"));

    }
}
