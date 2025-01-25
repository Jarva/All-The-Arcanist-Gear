package com.github.jarva.allthearcanistgear.setup.registry;


import com.github.jarva.allthearcanistgear.AllTheArcanistGear;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

@EventBusSubscriber(modid = AllTheArcanistGear.MODID, bus = EventBusSubscriber.Bus.MOD)
public class AddonAttributeRegistry {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(Registries.ATTRIBUTE, AllTheArcanistGear.MODID);
    public static final DeferredHolder<Attribute, Attribute> SPECTRAL_SIGHT = registerAttribute(AllTheArcanistGear.MODID + ".spectral_sight", (id) ->
            new RangedAttribute(id, 0.0, 0.0, 64.0).setSyncable(true)
    );

    public static DeferredHolder<Attribute, Attribute> registerAttribute(String name, Function<String, Attribute> attribute) {
        return ATTRIBUTES.register(name, () -> attribute.apply(name));
    }

    @SubscribeEvent
    public static void modifyEntityAttributes(EntityAttributeModificationEvent event) {
        for (EntityType<? extends LivingEntity> type : event.getTypes()) {
            if (type == EntityType.PLAYER) {
                for (DeferredHolder<Attribute, ? extends Attribute> attribute : ATTRIBUTES.getEntries()) {
                    event.add(type, attribute);
                }
            }
        }
    }
}
