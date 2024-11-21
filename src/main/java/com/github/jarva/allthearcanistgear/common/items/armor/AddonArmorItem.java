package com.github.jarva.allthearcanistgear.common.items.armor;

import com.github.jarva.allthearcanistgear.client.renderers.AddonArmorRenderer;
import com.github.jarva.allthearcanistgear.client.renderers.AddonGenericArmorModel;
import com.github.jarva.allthearcanistgear.setup.config.ArmorSetConfig;
import com.github.jarva.allthearcanistgear.setup.registry.AddonArmorMaterialRegistry;
import com.hollingsworth.arsnouveau.api.perk.PerkInstance;
import com.hollingsworth.arsnouveau.client.renderer.tile.GenericModel;
import com.hollingsworth.arsnouveau.common.items.data.ArmorPerkHolder;
import com.hollingsworth.arsnouveau.setup.registry.DataComponentRegistry;
import com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class AddonArmorItem extends ArmorItem implements GeoItem {
    private final int tier;

    public static ArmorPerkHolder getPerkHolder(int tier) {
        return new ArmorPerkHolder("", new ArrayList<>(), tier, new HashMap<>());
    }
    private final GenericModel<AddonArmorItem> model;
    private final ArmorSetConfig config;

    public AddonArmorItem(ArmorSetConfig config, Type slot, int tier) {
        super(
            AddonArmorMaterialRegistry.BASE,
            slot,
            ItemsRegistry.defaultItemProperties()
                .stacksTo(1)
                .fireResistant()
                .rarity(Rarity.EPIC)
                .component(DataComponentRegistry.ARMOR_PERKS, AddonArmorItem.getPerkHolder(tier)
            )
        );
        this.tier = tier;
        this.config = config;
        this.model = new AddonGenericArmorModel<AddonArmorItem>(config.name()).withEmptyAnim();
    }

    @Override
    public void verifyComponentsAfterLoad(ItemStack stack) {
        stack.update(DataComponentRegistry.ARMOR_PERKS, AddonArmorItem.getPerkHolder(tier), data -> data.setTier(tier));
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
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        ArmorPerkHolder data = stack.get(DataComponentRegistry.ARMOR_PERKS);
        if (data != null) {
            tooltipComponents.add(Component.translatable("ars_nouveau.tier", data.getTier() + 1).withStyle(ChatFormatting.GOLD));
            data.appendPerkTooltip(tooltipComponents, stack);
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
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        GeoItem.super.createGeoRenderer(consumer);
        consumer.accept(new GeoRenderProvider() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public @Nullable <T extends LivingEntity> HumanoidModel<?> getGeoArmorRenderer(@Nullable T livingEntity, ItemStack itemStack, @Nullable EquipmentSlot equipmentSlot, @Nullable HumanoidModel<T> original) {
                if (renderer == null) {
                    renderer = new AddonArmorRenderer(getArmorModel());
                }
                return renderer;
            }
        });
    }

    private GeoModel<AddonArmorItem> getArmorModel() {
        return model;
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack is) {
        ItemAttributeModifiers modifiers = config.buildAttributeMap(this);
        for (PerkInstance instance : is.get(DataComponentRegistry.ARMOR_PERKS).getPerkInstances(is)) {
            modifiers = instance.getPerk().applyAttributeModifiers(modifiers, is, instance.getSlot().value(), EquipmentSlotGroup.bySlot(getEquipmentSlot()));
        }
        return modifiers;
    }
}
