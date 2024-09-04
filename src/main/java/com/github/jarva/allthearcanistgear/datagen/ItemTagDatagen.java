package com.github.jarva.allthearcanistgear.datagen;

import alexthw.ars_elemental.common.items.armor.ArmorSet;
import alexthw.ars_elemental.registry.ModItems;
import com.github.jarva.allthearcanistgear.AllTheArcanistGear;
import com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry;
import com.thevortex.allthemodium.registry.ModRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ItemTagDatagen extends IntrinsicHolderTagsProvider<Item> {
    public static final TagKey<Item> BASE_HAT = ItemTags.create(AllTheArcanistGear.prefix("base_hat"));
    public static final TagKey<Item> BASE_CHEST = ItemTags.create(AllTheArcanistGear.prefix("base_chest"));
    public static final TagKey<Item> BASE_LEGS = ItemTags.create(AllTheArcanistGear.prefix("base_legs"));
    public static final TagKey<Item> BASE_BOOTS = ItemTags.create(AllTheArcanistGear.prefix("base_boots"));
    public static final TagKey<Item> ELEMENTAL_HAT = ItemTags.create(AllTheArcanistGear.prefix("elemental_hat"));
    public static final TagKey<Item> ELEMENTAL_CHEST = ItemTags.create(AllTheArcanistGear.prefix("elemental_chest"));
    public static final TagKey<Item> ELEMENTAL_LEGS = ItemTags.create(AllTheArcanistGear.prefix("elemental_legs"));
    public static final TagKey<Item> ELEMENTAL_BOOTS = ItemTags.create(AllTheArcanistGear.prefix("elemental_boots"));


    public static final TagKey<Item> ALLTHEMODIUM_INGOT = ItemTags.create(new ResourceLocation("forge:ingots/allthemodium"));
    public static final TagKey<Item> VIBRANIUM_INGOT = ItemTags.create(new ResourceLocation("forge:ingots/vibranium"));
    public static final TagKey<Item> UNOBTAINIUM_INGOT = ItemTags.create(new ResourceLocation("forge:ingots/unobtainium"));
    public static final TagKey<Item> ALLTHEMODIUM_SMITHING_TEMPLATE = ItemTags.create(AllTheArcanistGear.prefix("allthemodium_smithing"));
    public static final TagKey<Item> VIBRANIUM_SMITHING_TEMPLATE = ItemTags.create(AllTheArcanistGear.prefix("vibranium_smithing"));
    public static final TagKey<Item> UNOBTAINIUM_SMITHING_TEMPLATE = ItemTags.create(AllTheArcanistGear.prefix("unobtainium_smithing"));

    public ItemTagDatagen(PackOutput arg, CompletableFuture<HolderLookup.Provider> future, ExistingFileHelper helper) {
        super(arg, Registries.ITEM, future, item -> item.builtInRegistryHolder().key(), AllTheArcanistGear.MODID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        ArmorSet[] armorSets = new ArmorSet[]{ModItems.AIR_ARMOR, ModItems.EARTH_ARMOR, ModItems.FIRE_ARMOR, ModItems.WATER_ARMOR};

        IntrinsicTagAppender<Item> elementalHats = this.tag(ELEMENTAL_HAT);
        IntrinsicTagAppender<Item> elementalChest = this.tag(ELEMENTAL_CHEST);
        IntrinsicTagAppender<Item> elementalLegs = this.tag(ELEMENTAL_LEGS);
        IntrinsicTagAppender<Item> elementBoots = this.tag(ELEMENTAL_BOOTS);
        for (ArmorSet armorSet : armorSets) {
            elementalHats.addOptional(key(armorSet.getHat()));
            elementalChest.addOptional(key(armorSet.getChest()));
            elementalLegs.addOptional(key(armorSet.getLegs()));
            elementBoots.addOptional(key(armorSet.getBoots()));
        }

        this.tag(BASE_HAT)
            .add(ItemsRegistry.ARCANIST_HOOD.get())
            .add(ItemsRegistry.SORCERER_HOOD.get())
            .add(ItemsRegistry.BATTLEMAGE_HOOD.get())
            .addOptionalTag(ELEMENTAL_HAT.location());

        this.tag(BASE_CHEST)
            .add(ItemsRegistry.ARCANIST_ROBES.get())
            .add(ItemsRegistry.SORCERER_ROBES.get())
            .add(ItemsRegistry.BATTLEMAGE_ROBES.get())
            .addOptionalTag(ELEMENTAL_CHEST.location());

        this.tag(BASE_LEGS)
            .add(ItemsRegistry.ARCANIST_LEGGINGS.get())
            .add(ItemsRegistry.SORCERER_LEGGINGS.get())
            .add(ItemsRegistry.BATTLEMAGE_LEGGINGS.get())
            .addOptionalTag(ELEMENTAL_LEGS.location());

        this.tag(BASE_BOOTS)
            .add(ItemsRegistry.ARCANIST_BOOTS.get())
            .add(ItemsRegistry.SORCERER_BOOTS.get())
            .add(ItemsRegistry.BATTLEMAGE_BOOTS.get())
            .addOptionalTag(ELEMENTAL_BOOTS.location());

        this.tag(ALLTHEMODIUM_SMITHING_TEMPLATE)
            .addOptional(key(ModRegistry.ATM_SMITHING.get()));

        this.tag(VIBRANIUM_SMITHING_TEMPLATE)
                .addOptional(key(ModRegistry.VIB_SMITHING.get()));

        this.tag(UNOBTAINIUM_SMITHING_TEMPLATE)
                .addOptional(key(ModRegistry.UNO_SMITHING.get()));

        this.tag(ALLTHEMODIUM_INGOT)
                .addOptional(key(ModRegistry.ALLTHEMODIUM_INGOT.get()));

        this.tag(VIBRANIUM_INGOT)
                .addOptional(key(ModRegistry.VIBRANIUM_INGOT.get()));

        this.tag(UNOBTAINIUM_INGOT)
                .addOptional(key(ModRegistry.UNOBTAINIUM_INGOT.get()));
    }

    public ResourceLocation key(Item item) {
        return BuiltInRegistries.ITEM.getKey(item);
    }
}
