package com.github.jarva.allthearcanistgear.client.renderers;

import com.github.jarva.allthearcanistgear.common.armor.AddonArmorItem;
import com.hollingsworth.arsnouveau.client.renderer.tile.GenericModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

import java.util.Optional;

public class AddonArmorRenderer extends GeoArmorRenderer<AddonArmorItem> {
    public AddonArmorRenderer(GeoModel<AddonArmorItem> modelProvider) {
        super(modelProvider);
    }

    @Override
    public void renderRecursively(PoseStack poseStack, AddonArmorItem animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if(bone.getName().equalsIgnoreCase("armorRightArmSlim") || bone.getName().equalsIgnoreCase("armorLeftArmSlim")){
            bone.setHidden(true);
        }
        super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void actuallyRender(PoseStack poseStack, AddonArmorItem animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Optional<GeoBone> slimRight = model.getBone("armorRightArmSlim");
        Optional<GeoBone> slimLeft = model.getBone("armorLeftArmSlim");
        slimRight.ifPresent(geoBone -> geoBone.setHidden(true));
        slimLeft.ifPresent(geoBone -> geoBone.setHidden(true));
        model.getBone("armorRightArmSlim").ifPresent(geoBone -> geoBone.setHidden(true));
        model.getBone("armorLeftArmSlim").ifPresent(geoBone -> geoBone.setHidden(true));
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ResourceLocation getTextureLocation(AddonArmorItem instance) {
        if(instance != null && model instanceof GenericModel<AddonArmorItem> genericModel){
            return genericModel.textLoc;
        }

        return super.getTextureLocation(instance);
    }
}
