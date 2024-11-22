package com.github.jarva.allthearcanistgear.common.items.book;

import com.github.jarva.allthearcanistgear.client.renderers.AddonSpellBookRenderer;
import com.github.jarva.allthearcanistgear.datagen.BlockTagDatagen;
import com.github.jarva.allthearcanistgear.setup.config.ArmorSetConfig;
import com.github.jarva.allthearcanistgear.setup.config.ServerConfig;
import com.github.jarva.allthearcanistgear.setup.registry.AddonDataComponentRegistry;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.api.spell.SpellTier;
import com.hollingsworth.arsnouveau.common.items.SpellBook;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;

import java.util.function.Consumer;

public class AddonSpellBook extends SpellBook {
    private final ArmorSetConfig config;
    private final int itemTier;
    private static final int MAX_SLOTS = 10;

    public AddonSpellBook(ArmorSetConfig config, SpellTier spellTier, int tier) {
        super(new Item.Properties().stacksTo(1).component(AddonDataComponentRegistry.EXTENDED_GLYPH_CASTER, new ExtendedGlyphCasterData(MAX_SLOTS)), spellTier);
        this.config = config;
        this.itemTier = tier;
    }

    public ArmorSetConfig getConfig() {
        return config;
    }

    public int getItemTier() {
        return itemTier;
    }

    public boolean canBreak(SpellStats spellStats, BlockState state) {
        if (spellStats.getAmpMultiplier() < itemTier - 1) return false;
        if (state.is(BlockTagDatagen.UNOBTAINIUM_ORE) && itemTier >= 6) return true;
        if (state.is(BlockTagDatagen.VIBRANIUM_ORE) && itemTier >= 5) return true;
        return state.is(BlockTagDatagen.ALLTHEMODIUM_ORE) && itemTier >= 4;
    }

    @Override
    public void verifyComponentsAfterLoad(ItemStack stack) {
        stack.update(AddonDataComponentRegistry.EXTENDED_GLYPH_CASTER, new ExtendedGlyphCasterData(MAX_SLOTS), data -> {
            if (ServerConfig.SPEC.isLoaded()) {
                data.bonusSlots = config.bonusGlyphs().get();
            }
            return data;
        });
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private final BlockEntityWithoutLevelRenderer renderer = new AddonSpellBookRenderer();

            @Override
            public BlockEntityWithoutLevelRenderer getGeoItemRenderer() {
                return renderer;
            }
        });
    }
}
