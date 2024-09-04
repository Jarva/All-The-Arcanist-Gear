package com.github.jarva.allthearcanistgear.datagen;

import com.github.jarva.allthearcanistgear.AllTheArcanistGear;
import com.github.jarva.allthearcanistgear.common.armor.ArcanistArmorSet;
import com.github.jarva.allthearcanistgear.common.recipes.PerkTierIngredient;
import com.github.jarva.allthearcanistgear.setup.registry.AddonItemRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;
import java.util.function.Function;

public class RecipeDatagen extends RecipeProvider implements IConditionBuilder {
    public RecipeDatagen(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ConditionalRecipeOutput output = new ConditionalRecipeOutput(consumer);
        ConditionalRecipeOutput withATM = output.withCondition(modLoaded("allthemodium"));
        smithing(withATM, AddonItemRegistry.ALLTHEMODIUM, ItemTagDatagen.ALLTHEMODIUM_SMITHING_TEMPLATE, ItemTagDatagen.ALLTHEMODIUM_INGOT);
        smithing(withATM, AddonItemRegistry.VIBRANIUM, ItemTagDatagen.VIBRANIUM_SMITHING_TEMPLATE, AddonItemRegistry.ALLTHEMODIUM, ItemTagDatagen.VIBRANIUM_INGOT);
        smithing(withATM, AddonItemRegistry.UNOBTAINIUM, ItemTagDatagen.UNOBTAINIUM_SMITHING_TEMPLATE, AddonItemRegistry.VIBRANIUM, ItemTagDatagen.UNOBTAINIUM_INGOT);
    }

    private void smithing(ConditionalRecipeOutput consumer, ArcanistArmorSet set, TagKey<Item> template, TagKey<Item> modifier) {
        ICondition elementalLoaded = modLoaded("ars_elemental");

        ConditionalRecipeOutput withElemental = consumer.withCondition(elementalLoaded);
        smithing(withElemental, set.getHat(), template, ItemTagDatagen.ELEMENTAL_HAT, modifier, 0);
        smithing(withElemental, set.getChest(), template, ItemTagDatagen.ELEMENTAL_CHEST, modifier, 0);
        smithing(withElemental, set.getLegs(), template, ItemTagDatagen.ELEMENTAL_LEGS, modifier, 0);
        smithing(withElemental, set.getBoots(), template, ItemTagDatagen.ELEMENTAL_BOOTS, modifier, 0);

        ConditionalRecipeOutput withoutElemental = consumer.withCondition(not(elementalLoaded));
        smithing(withoutElemental, set.getHat(), template, ItemTagDatagen.BASE_HAT, modifier, 2);
        smithing(withoutElemental, set.getChest(), template, ItemTagDatagen.BASE_CHEST, modifier, 2);
        smithing(withoutElemental, set.getLegs(), template, ItemTagDatagen.BASE_LEGS, modifier, 2);
        smithing(withoutElemental, set.getBoots(), template, ItemTagDatagen.BASE_BOOTS, modifier, 2);
    }

    private Ingredient minTier(TagKey<Item> base, int tier) {
        return tier > 0 ? new PerkTierIngredient(base, tier) : Ingredient.of(base);
    }

    private void smithing(ConditionalRecipeOutput consumer, ArcanistArmorSet set, TagKey<Item> template, ArcanistArmorSet base, TagKey<Item> modifier) {
        smithing(consumer, set.getHat(), template, base.getHat(), modifier);
        smithing(consumer, set.getChest(), template, base.getChest(), modifier);
        smithing(consumer, set.getLegs(), template, base.getLegs(), modifier);
        smithing(consumer, set.getBoots(), template, base.getBoots(), modifier);
    }

    private void smithing(ConditionalRecipeOutput consumer, Item armor, TagKey<Item> template, Item base, TagKey<Item> modifier) {
        SmithingTransformRecipeBuilder.smithing(
                Ingredient.of(template),
                Ingredient.of(base),
                Ingredient.of(modifier),
                RecipeCategory.COMBAT,
                armor
        )
            .unlocks(AllTheArcanistGear.MODID + ":has_" + modifier.location().getPath() + "_ingot", has(modifier))
            .save(consumer, AllTheArcanistGear.prefix(getItemName(armor) + "_smithing"));
    }

    private void smithing(ConditionalRecipeOutput consumer, Item armor, TagKey<Item> template, TagKey<Item> base, TagKey<Item> modifier, int tier) {
        smithing(consumer, armor, template, minTier(base, tier), modifier, base.location().getPath());
    }

    private void smithing(ConditionalRecipeOutput consumer, Item armor, TagKey<Item> template, Ingredient base, TagKey<Item> modifier, String baseName) {
        SmithingTransformRecipeBuilder.smithing(
                Ingredient.of(template),
                base,
                Ingredient.of(modifier),
                RecipeCategory.COMBAT,
                armor
        )
            .unlocks(AllTheArcanistGear.MODID + ":has_" + modifier.location().getPath() + "_ingot", has(modifier))
            .save(consumer, AllTheArcanistGear.prefix(baseName + "_to_" + getItemName(armor) + "_smithing"));
    }
}
