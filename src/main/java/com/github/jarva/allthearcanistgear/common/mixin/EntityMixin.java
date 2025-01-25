package com.github.jarva.allthearcanistgear.common.mixin;

import com.github.jarva.allthearcanistgear.common.event.ArmorEvents;
import com.github.jarva.allthearcanistgear.setup.config.ArmorSetConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {
    @Inject(method = "fireImmune", at = @At(value = "HEAD"), cancellable = true)
    private void fireImmune(CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof LivingEntity living) {
            ArmorEvents.processArmorEvent(living, EquipmentSlot.CHEST, () -> true, ArmorSetConfig::preventFire, () -> cir.setReturnValue(true));
        }
    }
}
