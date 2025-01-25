package com.github.jarva.allthearcanistgear.common.items.book;

import com.github.jarva.allthearcanistgear.client.renderers.AddonSpellBookRenderer;
import com.github.jarva.allthearcanistgear.datagen.BlockTagDatagen;
import com.github.jarva.allthearcanistgear.setup.config.ArmorSetConfig;
import com.github.jarva.allthearcanistgear.setup.config.ServerConfig;
import com.github.jarva.allthearcanistgear.setup.registry.AddonDataComponentRegistry;
import com.hollingsworth.arsnouveau.api.ANFakePlayer;
import com.hollingsworth.arsnouveau.api.spell.SpellCaster;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.api.spell.SpellTier;
import com.hollingsworth.arsnouveau.common.items.SpellBook;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.hollingsworth.arsnouveau.setup.registry.DataComponentRegistry;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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

    public Integer getBlockTier(BlockState state) {
        if (state.is(BlockTagDatagen.UNOBTAINIUM_ORE)) return 6;
        if (state.is(BlockTagDatagen.VIBRANIUM_ORE)) return 5;
        if (state.is(BlockTagDatagen.ALLTHEMODIUM_ORE)) return 4;
        return null;
    }

    public Boolean canBreak(SpellStats spellStats, BlockState state, @NotNull LivingEntity caster) {
        double amps = spellStats.getAmpMultiplier();
        Integer tier = getBlockTier(state);
        if (tier == null) return null;

        if (amps < tier - 1) {
            PortUtil.sendMessageNoSpam(caster, Component.translatable("chat.allthearcanistgear.too_weak"));
            return false;
        }

        if (itemTier < tier) {
            PortUtil.sendMessageNoSpam(caster, Component.translatable("chat.allthearcanistgear.low_tier"));
            return false;
        }

        return true;
    }

    @Override
    public void verifyComponentsAfterLoad(ItemStack stack) {
        stack.update(AddonDataComponentRegistry.EXTENDED_GLYPH_CASTER, new ExtendedGlyphCasterData(MAX_SLOTS), data -> {
            @Nullable SpellCaster existing = stack.get(DataComponentRegistry.SPELL_CASTER);
            stack.remove(DataComponentRegistry.SPELL_CASTER);
            ExtendedGlyphCasterData updated = existing != null ? new ExtendedGlyphCasterData(data.getMaxSlots(), existing) : data;
            if (ServerConfig.SPEC.isLoaded()) {
                updated.bonusSlots = config.bonusGlyphs().get();
            }
            return updated;
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
