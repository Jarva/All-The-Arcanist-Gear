package com.github.jarva.allthearcanistgear.common.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hollingsworth.arsnouveau.api.perk.ArmorPerkHolder;
import com.hollingsworth.arsnouveau.api.perk.IPerkHolder;
import com.hollingsworth.arsnouveau.api.util.PerkUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.AbstractIngredient;
import net.minecraftforge.common.crafting.IIngredientSerializer;

public class PerkTierIngredient extends AbstractIngredient {
    public static final PerkTierIngredient.Serializer INSTANCE = new PerkTierIngredient.Serializer();
    private final int minLevel;
    private final TagKey<Item> tag;

    public static final MapCodec<PerkTierIngredient> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        TagKey.codec(Registries.ITEM).fieldOf("tag").forGetter(e -> e.tag),
            Codec.INT.fieldOf("min_level").forGetter(e -> e.minLevel)
    ).apply(instance, PerkTierIngredient::new));

    public PerkTierIngredient(TagKey<Item> tag, int level) {
        this.tag = tag;
        this.minLevel = level;
    }

    @Override
    public boolean test(ItemStack itemStack) {
        if (!itemStack.is(tag)) return false;
        IPerkHolder<ItemStack> data = PerkUtil.getPerkHolder(itemStack);
        if (data instanceof ArmorPerkHolder armorPerkHolder) {
            return armorPerkHolder.getTier() >= minLevel;
        }
        return false;
    }

    @Override
    public boolean isSimple() {
        return false;
    }

    @Override
    public IIngredientSerializer<? extends Ingredient> getSerializer() {
        return INSTANCE;
    }

    @Override
    public JsonElement toJson() {
        return CODEC.codec().encodeStart(JsonOps.INSTANCE, this).result().get();
    }

    public static class Serializer implements IIngredientSerializer<PerkTierIngredient> {
        @Override
        public PerkTierIngredient parse(FriendlyByteBuf arg) {
            return arg.readJsonWithCodec(CODEC.codec());
        }

        @Override
        public PerkTierIngredient parse(JsonObject jsonObject) {
            return CODEC.codec().parse(JsonOps.INSTANCE, jsonObject).result().get();
        }

        @Override
        public void write(FriendlyByteBuf arg, PerkTierIngredient arg2) {
            arg.writeJsonWithCodec(CODEC.codec(), arg2);
        }
    }
}
