package com.github.jarva.allthearcanistgear.common.event;

import com.github.jarva.allthearcanistgear.AllTheArcanistGear;
import com.github.jarva.allthearcanistgear.common.items.perks.TruesightPerk;
import com.hollingsworth.arsnouveau.api.perk.Perk;
import com.hollingsworth.arsnouveau.api.util.PerkUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

import java.util.function.Supplier;

@EventBusSubscriber(modid = AllTheArcanistGear.MODID, bus = EventBusSubscriber.Bus.GAME)
public class PerkEvents {
    @SubscribeEvent
    public static void onEffectApplied(MobEffectEvent.Applicable event) {
        LivingEntity entity = event.getEntity();
        MobEffectInstance instance = event.getEffectInstance();
        if (instance == null) return;
        processPerkEvent(entity, () -> instance.is(MobEffects.BLINDNESS), 2, TruesightPerk.INSTANCE, () -> event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY));
        processPerkEvent(entity, () -> instance.is(MobEffects.DARKNESS), 3, TruesightPerk.INSTANCE, () -> event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY));
    }

    private static void processPerkEvent(LivingEntity entity, Supplier<Boolean> predicate, int minLevel, Perk instance, Runnable cancel) {
        if (!predicate.get()) return;
        int level = PerkUtil.countForPerk(instance, entity);
        if (level >= minLevel) {
            cancel.run();
        }
    }
}
