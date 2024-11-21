package com.github.jarva.allthearcanistgear.setup.registry;

import com.github.jarva.allthearcanistgear.common.items.book.ExtendedGlyphCasterData;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.hollingsworth.arsnouveau.ArsNouveau.MODID;

public class AddonDataComponentRegistry {
    public static final DeferredRegister<DataComponentType<?>> DATA = DeferredRegister.create(BuiltInRegistries.DATA_COMPONENT_TYPE, MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ExtendedGlyphCasterData>> EXTENDED_GLYPH_CASTER = DATA.register("extended_glyph_caster", () -> DataComponentType.<ExtendedGlyphCasterData>builder().persistent(ExtendedGlyphCasterData.CODEC.codec()).networkSynchronized(ExtendedGlyphCasterData.STREAM_CODEC).build());
}
