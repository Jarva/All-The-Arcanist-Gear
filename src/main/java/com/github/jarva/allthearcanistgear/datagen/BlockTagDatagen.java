package com.github.jarva.allthearcanistgear.datagen;

import com.github.jarva.allthearcanistgear.AllTheArcanistGear;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class BlockTagDatagen  extends IntrinsicHolderTagsProvider<Block> {
    public static final TagKey<Block> ALLTHEMODIUM_ORE = BlockTags.create(common("ores/allthemodium"));
    public static final TagKey<Block> VIBRANIUM_ORE = BlockTags.create(common("ores/vibranium"));
    public static final TagKey<Block> UNOBTAINIUM_ORE = BlockTags.create(common("ores/unobtainium"));

    public BlockTagDatagen(PackOutput arg, CompletableFuture<HolderLookup.Provider> future, ExistingFileHelper helper) {
        super(arg, Registries.BLOCK, future, item -> item.builtInRegistryHolder().key(), AllTheArcanistGear.MODID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

    }

    private static ResourceLocation common(String path) {
        return ResourceLocation.fromNamespaceAndPath("c", path);
    }
}
