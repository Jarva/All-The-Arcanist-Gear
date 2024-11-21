package com.github.jarva.allthearcanistgear.common.mixin;

import com.github.jarva.allthearcanistgear.common.items.book.AddonSpellBook;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectBreak;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = EffectBreak.class, remap = false)
public class EffectBreakMixin {
    @WrapOperation(method = "onResolveBlock", at = @At(value = "INVOKE", target = "Lcom/hollingsworth/arsnouveau/common/spell/effect/EffectBreak;canBlockBeHarvested(Lcom/hollingsworth/arsnouveau/api/spell/SpellStats;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)Z"))
    private boolean onResolveBlock(EffectBreak instance, SpellStats spellStats, Level level, BlockPos blockPos, Operation<Boolean> original, @Local(argsOnly = true) SpellContext spellContext) {
        if (spellContext.getCasterTool().getItem() instanceof AddonSpellBook addonSpellBook) {
            BlockState state = level.getBlockState(blockPos);
            return addonSpellBook.canBreak(spellStats, state);
        }
        return original.call(instance, spellStats, level, blockPos);
    }
}
