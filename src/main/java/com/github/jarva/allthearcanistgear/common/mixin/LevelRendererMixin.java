package com.github.jarva.allthearcanistgear.common.mixin;

import com.github.jarva.allthearcanistgear.setup.registry.AddonAttributeRegistry;
import com.hollingsworth.arsnouveau.common.lib.EntityTags;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
    @Shadow @Final private Minecraft minecraft;

    @WrapOperation(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;shouldEntityAppearGlowing(Lnet/minecraft/world/entity/Entity;)Z"))
    private boolean shouldEntityAppearGlowing(Minecraft instance, Entity entity, Operation<Boolean> original) {
        if (!(entity instanceof LivingEntity)) return original.call(instance, entity);
        double spectral = instance.player.getAttributeValue(AddonAttributeRegistry.SPECTRAL_SIGHT);
        if (spectral == 0.0D) return original.call(instance, entity);

        return entity.distanceTo(instance.player) <= spectral * 16;
    }

    @WrapOperation(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getTeamColor()I"))
    private int getTeamColor(Entity entity, Operation<Integer> original) {
        double spectral = this.minecraft.player.getAttributeValue(AddonAttributeRegistry.SPECTRAL_SIGHT);

        if (spectral == 0.0D) return original.call(entity);

        int color = original.call(entity);
        if (color != 16777215) return color;

        if (entity.getType().is(EntityTags.MAGIC_FIND)) {
            return 16718260;
        }

        return entity.getType().getCategory().isFriendly() ? 747043 : 8129026;
    }
}
