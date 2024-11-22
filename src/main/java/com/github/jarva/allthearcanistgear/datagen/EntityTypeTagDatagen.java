package com.github.jarva.allthearcanistgear.datagen;

import com.github.jarva.allthearcanistgear.AllTheArcanistGear;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class EntityTypeTagDatagen extends IntrinsicHolderTagsProvider<EntityType<?>> {
    public static final TagKey<EntityType<?>> BONUS_DAMAGE = TagKey.create(Registries.ENTITY_TYPE, AllTheArcanistGear.prefix("bonus_damage"));

    public EntityTypeTagDatagen(PackOutput arg, CompletableFuture<HolderLookup.Provider> future, ExistingFileHelper helper) {
        super(arg, Registries.ENTITY_TYPE, future, item -> item.builtInRegistryHolder().key(), AllTheArcanistGear.MODID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(BONUS_DAMAGE)
                .addOptional(ResourceLocation.fromNamespaceAndPath("allthemodium", "piglich"));
    }
}
