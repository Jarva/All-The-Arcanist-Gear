package com.github.jarva.allthearcanistgear.common.items.book;

import com.github.jarva.allthearcanistgear.setup.registry.AddonDataComponentRegistry;
import com.hollingsworth.arsnouveau.api.spell.AbstractCaster;
import com.hollingsworth.arsnouveau.api.spell.SpellSlotMap;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.function.Function;

public class ExtendedGlyphCasterData extends AbstractCaster<ExtendedGlyphCasterData> {
    private static final MapCodec<ExtendedGlyphCasterData> BASE_CODEC = createCodec(ExtendedGlyphCasterData::new);
    public static final MapCodec<ExtendedGlyphCasterData> CODEC = Codec.mapPair(
            BASE_CODEC,
            Codec.INT.fieldOf("bonus_slots")
    ).xmap(pair -> {
        ExtendedGlyphCasterData data = pair.getFirst();
        data.bonusSlots = pair.getSecond();
        return data;
    }, instance -> Pair.of(instance, instance.bonusSlots));

    private static final StreamCodec<RegistryFriendlyByteBuf, ExtendedGlyphCasterData> BASE_STREAM_CODEC = createStream(ExtendedGlyphCasterData::new);
    public static final StreamCodec<RegistryFriendlyByteBuf, ExtendedGlyphCasterData> STREAM_CODEC = StreamCodec.composite(
            BASE_STREAM_CODEC, Function.identity(), ByteBufCodecs.INT, s -> s.bonusSlots, ExtendedGlyphCasterData::fromStream
    );

    public int bonusSlots = 0;

    public ExtendedGlyphCasterData(int maxSlots){
        super(maxSlots);
    }

    public ExtendedGlyphCasterData(Integer slot, String flavorText, Boolean isHidden, String hiddenText, int maxSlots, SpellSlotMap spells){
        super(slot, flavorText, isHidden, hiddenText, maxSlots, spells);
    }

    private static ExtendedGlyphCasterData fromStream(ExtendedGlyphCasterData data, int bonusSlots) {
        data.bonusSlots = bonusSlots;
        return data;
    }

    @Override
    public int getBonusGlyphSlots() {
        return bonusSlots;
    }

    @Override
    public MapCodec<ExtendedGlyphCasterData> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, ExtendedGlyphCasterData> streamCodec() {
        return STREAM_CODEC;
    }

    @Override
    public DataComponentType<ExtendedGlyphCasterData> getComponentType() {
        return AddonDataComponentRegistry.EXTENDED_GLYPH_CASTER.get();
    }

    @Override
    protected ExtendedGlyphCasterData build(int slot, String flavorText, Boolean isHidden, String hiddenText, int maxSlots, SpellSlotMap spells) {
        return new ExtendedGlyphCasterData(slot, flavorText, isHidden, hiddenText, maxSlots, spells);
    }
}
