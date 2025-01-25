package com.github.jarva.allthearcanistgear.common.items.perks;

import com.github.jarva.allthearcanistgear.AllTheArcanistGear;
import com.github.jarva.allthearcanistgear.setup.registry.AddonAttributeRegistry;
import com.hollingsworth.arsnouveau.api.perk.Perk;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.jetbrains.annotations.NotNull;

public class SpectralSightPerk extends Perk {
    public static final SpectralSightPerk INSTANCE = new SpectralSightPerk();

    public SpectralSightPerk() {
        super(AllTheArcanistGear.prefix("thread_spectral_sight"));
    }

    @Override
    public String getLangName() {
        return "Spectral Sight";
    }

    @Override
    public String getLangDescription() {
        return "Grants spectral sight, allowing you to see creatures through walls. Gives sight in a radius of 8 blocks per level.";
    }

    @Override
    public @NotNull ItemAttributeModifiers applyAttributeModifiers(ItemAttributeModifiers modifiers, ItemStack stack, int slotValue, EquipmentSlotGroup equipmentSlotGroup) {
        return modifiers.withModifierAdded(
                AddonAttributeRegistry.SPECTRAL_SIGHT, new AttributeModifier(INSTANCE.getRegistryName(), slotValue, AttributeModifier.Operation.ADD_VALUE), equipmentSlotGroup
        );
    }
}
