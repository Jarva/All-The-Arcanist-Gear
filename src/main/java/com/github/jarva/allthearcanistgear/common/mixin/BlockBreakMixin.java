package com.github.jarva.allthearcanistgear.common.mixin;

import com.github.jarva.allthearcanistgear.common.items.book.AddonSpellBook;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.thevortex.allthemodium.events.BlockBreak;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.util.FakePlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = BlockBreak.class, remap = false)
public class BlockBreakMixin {
    @WrapOperation(method = "on(Lnet/neoforged/neoforge/event/level/BlockEvent$BreakEvent;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isCreative()Z"))
    private static boolean onBreak(Player instance, Operation<Boolean> original) {
        if (instance instanceof FakePlayer fakePlayer && fakePlayer.getMainHandItem().getItem() instanceof AddonSpellBook) {
            return true;
        }
        return original.call(instance);
    }
}
