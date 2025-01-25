package com.github.jarva.allthearcanistgear.common.items.perks;

import com.github.jarva.allthearcanistgear.AllTheArcanistGear;
import com.hollingsworth.arsnouveau.api.perk.Perk;
import com.hollingsworth.arsnouveau.api.perk.PerkSlot;

import com.github.jarva.allthearcanistgear.setup.registry.ArsNouveauRegistry;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.common.NeoForgeMod;
import org.jetbrains.annotations.NotNull;

public class FlightPerk extends Perk {
    public static final FlightPerk INSTANCE = new FlightPerk();

    public FlightPerk() {
        super(AllTheArcanistGear.prefix("thread_flight"));
    }

    @Override
    public PerkSlot minimumSlot() {
        return ArsNouveauRegistry.PERK_FOUR;
    }

    @Override
    public String getLangName() {
        return "Flight";
    }

    @Override
    public String getLangDescription() {
        return "Grants creative flight. Must be equipped in a slot of at least level 4.";
    }

    @Override
    public @NotNull ItemAttributeModifiers applyAttributeModifiers(ItemAttributeModifiers modifiers, ItemStack stack, int slotValue, EquipmentSlotGroup equipmentSlotGroup) {
        return modifiers.withModifierAdded(
                NeoForgeMod.CREATIVE_FLIGHT, new AttributeModifier(INSTANCE.getRegistryName(), 1, AttributeModifier.Operation.ADD_VALUE), equipmentSlotGroup
        );
    }
}
