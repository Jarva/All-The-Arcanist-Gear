package com.github.jarva.allthearcanistgear.common.recipes;

import com.github.jarva.allthearcanistgear.setup.registry.AddonIngredientTypeRegistry;
import com.hollingsworth.arsnouveau.common.items.data.ArmorPerkHolder;
import com.hollingsworth.arsnouveau.setup.registry.DataComponentRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.crafting.ICustomIngredient;
import net.neoforged.neoforge.common.crafting.IngredientType;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class PerkTierIngredient implements ICustomIngredient {
    private final int minLevel;
    private final TagKey<Item> tag;

    public static final MapCodec<PerkTierIngredient> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        TagKey.codec(Registries.ITEM).fieldOf("tag").forGetter(e -> e.tag),
            Codec.INT.fieldOf("min_level").forGetter(e -> e.minLevel)
    ).apply(instance, PerkTierIngredient::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, PerkTierIngredient> STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistries(CODEC.codec());

    public PerkTierIngredient(TagKey<Item> tag, int level) {
        this.tag = tag;
        this.minLevel = level;
    }

    @Override
    public boolean test(ItemStack itemStack) {
        @Nullable ArmorPerkHolder data = itemStack.get(DataComponentRegistry.ARMOR_PERKS);
        if (data == null) return false;
        return data.getTier() >= minLevel;
    }

    @Override
    public Stream<ItemStack> getItems() {
        return BuiltInRegistries.ITEM.getOrCreateTag(tag).stream().map(ItemStack::new).map(is -> {
            @Nullable ArmorPerkHolder data = is.get(DataComponentRegistry.ARMOR_PERKS);
            if (data == null) return null;
            is.set(DataComponentRegistry.ARMOR_PERKS, data.setTier(minLevel));
            return is;
        });
    }

    @Override
    public boolean isSimple() {
        return false;
    }

    @Override
    public IngredientType<?> getType() {
        return AddonIngredientTypeRegistry.PERK_TIER.get();
    }
}
