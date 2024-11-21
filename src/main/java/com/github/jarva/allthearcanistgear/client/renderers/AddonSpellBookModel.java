package com.github.jarva.allthearcanistgear.client.renderers;

import com.github.jarva.allthearcanistgear.AllTheArcanistGear;
import com.github.jarva.allthearcanistgear.common.items.book.AddonSpellBook;
import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.client.renderer.item.SpellBookModel;
import com.hollingsworth.arsnouveau.client.renderer.item.TransformAnimatedModel;
import com.hollingsworth.arsnouveau.common.items.SpellBook;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animation.AnimationState;

public class AddonSpellBookModel extends TransformAnimatedModel<AddonSpellBook> {
    public ResourceLocation modelLoc;

    public AddonSpellBookModel(ResourceLocation modelLocation) {
        this.modelLoc = modelLocation;
    }

    @Override
    public void setCustomAnimations(AddonSpellBook entity, long uniqueID, @org.jetbrains.annotations.Nullable AnimationState customPredicate) {
        super.setCustomAnimations(entity, uniqueID, customPredicate);
    }

    @Override
    public ResourceLocation getModelResource(AddonSpellBook object) {
        return getModelResource(object, null);
    }

    @Override
    public ResourceLocation getModelResource(AddonSpellBook object, @Nullable ItemDisplayContext transformType) {
//        return modelLoc;
        if (transformType == ItemDisplayContext.GUI || transformType == ItemDisplayContext.FIXED) {
            return SpellBookModel.CLOSED;
        }
        return modelLoc;
    }


    @Override
    public ResourceLocation getTextureResource(AddonSpellBook book) {
        return AllTheArcanistGear.prefix( "textures/item/" + book.getConfig().name() + "_spell_book.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AddonSpellBook animatable) {
        return ArsNouveau.prefix( "animations/empty.json");
    }

    @Override
    public RenderType getRenderType(AddonSpellBook animatable, ResourceLocation texture) {
        return RenderType.entityCutoutNoCull(texture);
    }
}
