package com.github.jarva.allthearcanistgear.datagen;

import com.github.jarva.allthearcanistgear.common.items.perks.FlightPerk;
import com.github.jarva.allthearcanistgear.common.items.perks.SpectralSightPerk;
import com.github.jarva.allthearcanistgear.common.items.perks.TruesightPerk;
import com.github.jarva.allthearcanistgear.common.items.perks.VitalityPerk;
import com.hollingsworth.arsnouveau.api.perk.Perk;
import com.hollingsworth.arsnouveau.common.crafting.recipes.EnchantingApparatusRecipe;
import com.hollingsworth.arsnouveau.common.datagen.ApparatusRecipeBuilder;
import com.hollingsworth.arsnouveau.common.datagen.ApparatusRecipeProvider;
import com.hollingsworth.arsnouveau.common.items.PerkItem;
import com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

import java.nio.file.Path;

public class ApparatusRecipeDatagen extends ApparatusRecipeProvider {
    public ApparatusRecipeDatagen(DataGenerator generatorIn) {
        super(generatorIn);
    }

    protected static Path getRecipePath(Path pathIn, String str) {
        return pathIn.resolve("data/" + Setup.root + "/recipe/apparatus/" + str + ".json");
    }

    @Override
    public void collectJsons(CachedOutput pOutput) {
        this.addEntries();

        for (ApparatusRecipeBuilder.RecipeWrapper<? extends EnchantingApparatusRecipe> recipe : this.recipes) {
            Path path = getRecipePath(this.output, recipe.id().getPath());
            this.saveStable(pOutput, recipe.serialize(), path);
        }
    }

    @Override
    public void addEntries() {
        buildThread(getPerkItem(FlightPerk.INSTANCE), ItemsRegistry.AIR_ESSENCE, Items.PHANTOM_MEMBRANE, ItemsRegistry.AIR_ESSENCE, Items.PHANTOM_MEMBRANE, ItemsRegistry.AIR_ESSENCE, Items.PHANTOM_MEMBRANE);
        buildThread(getPerkItem(TruesightPerk.INSTANCE), Items.GOLDEN_CARROT, Items.GOLDEN_CARROT, Items.GOLDEN_CARROT);
        buildThread(getPerkItem(VitalityPerk.INSTANCE), Items.ENCHANTED_GOLDEN_APPLE, Items.GLISTERING_MELON_SLICE, Items.GLISTERING_MELON_SLICE);
        buildThread(getPerkItem(SpectralSightPerk.INSTANCE), Items.SPECTRAL_ARROW, Items.GOLDEN_CARROT, Items.GLOW_INK_SAC);
    }

    public void buildThread(PerkItem output, ItemLike... pedestal) {
        buildThread(ItemsRegistry.BLANK_THREAD, output, pedestal);
    }

    public void buildThread(PerkItem reagent, PerkItem output, ItemLike... pedestal) {
        ApparatusRecipeBuilder build = builder().withReagent(ItemsRegistry.BLANK_THREAD).withResult(output);

        for (ItemLike item : pedestal) {
            build = build.withPedestalItem(item);
        }

        addRecipe(build.build());
    }

    public PerkItem getPerkItem(Perk id) {
        return super.getPerkItem(id.getRegistryName());
    }
}
