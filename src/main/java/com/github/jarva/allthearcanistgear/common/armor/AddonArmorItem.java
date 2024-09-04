package com.github.jarva.allthearcanistgear.common.armor;

import com.github.jarva.allthearcanistgear.client.renderers.AddonArmorRenderer;
import com.github.jarva.allthearcanistgear.client.renderers.AddonGenericArmorModel;
import com.github.jarva.allthearcanistgear.setup.config.ArmorSetConfig;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.hollingsworth.arsnouveau.api.perk.*;
import com.hollingsworth.arsnouveau.api.registry.PerkRegistry;
import com.hollingsworth.arsnouveau.api.util.PerkUtil;
import com.hollingsworth.arsnouveau.client.renderer.tile.GenericModel;
import com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.function.Consumer;

public class AddonArmorItem extends ArmorItem implements GeoItem {
    private final int tier;

    private final GenericModel<AddonArmorItem> model;
    private final ArmorSetConfig config;

    public AddonArmorItem(ArmorSetConfig config, Type slot, int tier) {
        super(
            ArcanistMaterial.INSTANCE,
            slot,
            ItemsRegistry.defaultItemProperties()
                .stacksTo(1)
                .fireResistant()
                .rarity(Rarity.EPIC)
        );
        this.tier = tier;
        this.config = config;
        this.model = new AddonGenericArmorModel<AddonArmorItem>(config.name()).withEmptyAnim();
    }

    @Override
    public void verifyTagAfterLoad(CompoundTag compoundTag) {
        super.verifyTagAfterLoad(compoundTag);
        compoundTag.getCompound("an_stack_perks").putInt("tier", tier);
    }

    public ArmorSetConfig getConfig() {
        return config;
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, world, tooltipComponents, tooltipFlag);
        IPerkProvider<ItemStack> data = PerkRegistry.getPerkProvider(this);
        if (data != null) {
            if (data.getPerkHolder(stack) instanceof ArmorPerkHolder armorPerkHolder) {
                tooltipComponents.add(Component.translatable("ars_nouveau.tier", armorPerkHolder.getTier() + 1).withStyle(ChatFormatting.GOLD));
            }
            data.getPerkHolder(stack).appendPerkTooltip(tooltipComponents, stack);
        }
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        return true;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {}

    AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IClientItemExtensions() {
            private AddonArmorRenderer renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (renderer == null) {
                    renderer = new AddonArmorRenderer(getArmorModel());
                }
                renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return renderer;
            }
        });
    }

    private GeoModel<AddonArmorItem> getArmorModel() {
        return model;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> attributes = new ImmutableMultimap.Builder();
        attributes.putAll(getDefaultAttributeModifiers(slot));
        if (slot != getEquipmentSlot()) {
            return attributes.build();
        }

        IPerkHolder<ItemStack> perkHolder = PerkUtil.getPerkHolder(stack);
        if (perkHolder == null) return attributes.build();
        for (PerkInstance perkInstance : perkHolder.getPerkInstances()) {
            IPerk perk = perkInstance.getPerk();
            attributes.putAll(perk.getModifiers(slot, stack, perkInstance.getSlot().value));
        }
        return attributes.build();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        if (slot != getEquipmentSlot()) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            return builder.build();
        }
        return config.buildAttributeMap(this);
    }
}
