package com.github.jarva.allthearcanistgear.common.items.perks;

import com.github.jarva.allthearcanistgear.AllTheArcanistGear;
import com.hollingsworth.arsnouveau.api.perk.Perk;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.jetbrains.annotations.NotNull;

public class VitalityPerk extends Perk {
    public static final VitalityPerk INSTANCE = new VitalityPerk();

    public VitalityPerk() {
        super(AllTheArcanistGear.prefix("thread_vitality"));
    }

    @Override
    public String getLangName() {
        return "Vitality";
    }

    @Override
    public String getLangDescription() {
        return "Grants an extra heart for each level.";
    }

    @Override
    public @NotNull ItemAttributeModifiers applyAttributeModifiers(ItemAttributeModifiers modifiers, ItemStack stack, int slotValue, EquipmentSlotGroup equipmentSlotGroup) {
        return modifiers.withModifierAdded(
                Attributes.MAX_HEALTH, new AttributeModifier(INSTANCE.getRegistryName(), slotValue*2, AttributeModifier.Operation.ADD_VALUE), equipmentSlotGroup
        );
    }
}
