package com.github.jarva.allthearcanistgear.common.items.perks;

import com.github.jarva.allthearcanistgear.AllTheArcanistGear;
import com.hollingsworth.arsnouveau.api.perk.Perk;
import de.dafuqs.additionalentityattributes.AdditionalEntityAttributes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.jetbrains.annotations.NotNull;

public class TruesightPerk extends Perk {
    public static final TruesightPerk INSTANCE = new TruesightPerk();

    public TruesightPerk() {
        super(AllTheArcanistGear.prefix("thread_truesight"));
    }

    @Override
    public String getLangName() {
        return "True Sight";
    }

    @Override
    public String getLangDescription() {
        return "Grants True Sight, allowing you to see more of your surroundings. Tier 1 grants Nightvision, Tier 2 grants immunity to Blindness, Tier 3 grants immunity to Darkness, Tier 4 grants lava vision.";
    }

    @Override
    public void onAdded(LivingEntity entity) {
        entity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, -1, 0, true, false));
    }

    @Override
    public void onRemoved(LivingEntity entity) {
        MobEffectInstance effect = entity.getEffect(MobEffects.NIGHT_VISION);
        if (effect != null && effect.isAmbient() && effect.getDuration() == -1) {
            entity.removeEffect(MobEffects.NIGHT_VISION);
        }
    }

    @Override
    public @NotNull ItemAttributeModifiers applyAttributeModifiers(ItemAttributeModifiers modifiers, ItemStack stack, int slotValue, EquipmentSlotGroup equipmentSlotGroup) {
        return modifiers.withModifierAdded(
                AdditionalEntityAttributes.LAVA_VISIBILITY, new AttributeModifier(INSTANCE.getRegistryName(), slotValue > 3 ? 64 : 0, AttributeModifier.Operation.ADD_VALUE), equipmentSlotGroup
        );
    }
}
