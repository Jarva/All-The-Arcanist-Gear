package com.github.jarva.allthearcanistgear.datagen;

import com.hollingsworth.arsnouveau.common.datagen.patchouli.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;

import static com.hollingsworth.arsnouveau.setup.registry.RegistryHelper.getRegistryName;

public class PatchouliDatagen extends com.hollingsworth.arsnouveau.common.datagen.PatchouliProvider {

    public PatchouliDatagen(DataGenerator generatorIn, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(generatorIn, lookupProvider);
    }

    @Override
    public void collectJsons(CachedOutput cache) {
//        addPage(new PatchouliBuilder(EQUIPMENT, AddonItemRegistry.ALLTHEMODIUM.getHat())
//                        .withTextPage("allthearcanistgear.page.allthemodium"),
//                getPath(EQUIPMENT, "allthemodium")
//        );

        for (PatchouliPage patchouliPage : pages) {
            saveStable(cache, patchouliPage.build(), patchouliPage.path());
        }
    }

    @Override
    public PatchouliPage addBasicItem(ItemLike item, ResourceLocation category, IPatchouliPage recipePage) {
        PatchouliBuilder builder = new PatchouliBuilder(category, item.asItem().getDescriptionId())
                .withIcon(item.asItem())
                .withPage(new TextPage(Setup.root + ".page." + getRegistryName(item.asItem()).getPath()));
        if (recipePage != null) {
            builder = builder.withPage(recipePage);
        }
        PatchouliPage page = new PatchouliPage(builder, getPath(category, getRegistryName(item.asItem()).getPath()));
        this.pages.add(page);
        return page;
    }
}
