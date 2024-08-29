package com.github.jarva.allthearcanistgear.client.renderers;

import com.github.jarva.allthearcanistgear.AllTheArcanistGear;
import com.hollingsworth.arsnouveau.client.renderer.tile.GenericModel;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animatable.GeoAnimatable;

public class AddonGenericArmorModel<T extends GeoAnimatable> extends GenericModel<T> {
    public AddonGenericArmorModel(String name) {
        super(name);
        this.textPathRoot = "item";
        this.modelLocation = ResourceLocation.fromNamespaceAndPath(AllTheArcanistGear.MODID, "geo/arcanist_armor.geo.json");
        this.textLoc = ResourceLocation.fromNamespaceAndPath(AllTheArcanistGear.MODID, "textures/item/arcanist_armor/" + name + ".png");
        this.animationLoc = ResourceLocation.fromNamespaceAndPath(AllTheArcanistGear.MODID, "animations/" + name + "_animations.json");
        this.name = name;
    }
}
