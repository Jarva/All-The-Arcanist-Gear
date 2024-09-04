package com.github.jarva.allthearcanistgear.common.event;

import com.github.jarva.allthearcanistgear.AllTheArcanistGear;
import com.github.jarva.allthearcanistgear.common.armor.AddonArmorItem;
import com.github.jarva.allthearcanistgear.setup.config.ArmorSetConfig;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Function;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = AllTheArcanistGear.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ArmorEvents {
    @SubscribeEvent
    public static void onPlayerFall(LivingFallEvent event) {
        processArmorEvent(event.getEntity(), EquipmentSlot.FEET, () -> true, ArmorSetConfig::preventFallDamage, () -> event.setCanceled(true));
    }

    @SubscribeEvent
    public static void onEntityHurt(LivingDamageEvent event) {
        if (event.getEntity().getCommandSenderWorld().isClientSide()) return;

        LivingEntity entity = event.getEntity();

        processArmorEvent(entity, EquipmentSlot.HEAD, () -> event.getSource().is(DamageTypeTags.IS_DROWNING), ArmorSetConfig::preventKinetic, () -> {
            entity.setAirSupply(entity.getMaxAirSupply());
            event.setCanceled(true);
        });
        processArmorEvent(entity, EquipmentSlot.HEAD, () -> event.getSource().is(DamageTypes.FLY_INTO_WALL), ArmorSetConfig::preventKinetic, () -> event.setCanceled(true));
        processArmorEvent(entity, EquipmentSlot.CHEST, () -> event.getSource().is(DamageTypeTags.IS_FIRE), ArmorSetConfig::preventFire, () -> event.setCanceled(true));
        processArmorEvent(entity, EquipmentSlot.CHEST, () -> event.getSource().is(DamageTypes.DRAGON_BREATH), ArmorSetConfig::preventDragonsBreath, () -> event.setCanceled(true));
        processArmorEvent(entity, EquipmentSlot.LEGS, () -> event.getSource().is(DamageTypes.WITHER), ArmorSetConfig::preventWither, () -> event.setCanceled(true));
    }

    @SubscribeEvent
    public static void onEffectApplied(MobEffectEvent.Applicable event) {
        LivingEntity entity = event.getEntity();
        MobEffectInstance instance = event.getEffectInstance();
        processArmorEvent(entity, EquipmentSlot.LEGS, () -> instance.getEffect().equals(MobEffects.WITHER), ArmorSetConfig::preventWither, () -> event.setResult(MobEffectEvent.Applicable.Result.DENY));
        processArmorEvent(entity, EquipmentSlot.LEGS, () -> instance.getEffect().equals(MobEffects.LEVITATION), ArmorSetConfig::preventLevitation, () -> event.setResult(MobEffectEvent.Applicable.Result.DENY));
    }

    private static void processArmorEvent(LivingEntity entity, EquipmentSlot slot, Supplier<Boolean> predicate, Function<ArmorSetConfig, Supplier<Boolean>> configFn, Runnable cancel) {
        if (entity.getItemBySlot(slot).getItem() instanceof AddonArmorItem armorItem && configFn.apply(armorItem.getConfig()).get() && predicate.get()) {
            cancel.run();
        }
    }
}
