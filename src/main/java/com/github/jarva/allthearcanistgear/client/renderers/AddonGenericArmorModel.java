package com.github.jarva.allthearcanistgear.client.renderers;

import com.github.jarva.allthearcanistgear.AllTheArcanistGear;
import com.hollingsworth.arsnouveau.client.renderer.tile.GenericModel;
import software.bernie.geckolib.core.animatable.GeoAnimatable;

public class AddonGenericArmorModel<T extends GeoAnimatable> extends GenericModel<T> {
    public AddonGenericArmorModel(String name) {
        super(name);
        this.textPathRoot = "item";
        this.modelLocation = AllTheArcanistGear.prefix("geo/arcanist_armor.geo.json");
        this.textLoc = AllTheArcanistGear.prefix("textures/item/arcanist_armor/" + name + ".png");
        this.animationLoc = AllTheArcanistGear.prefix("animations/" + name + "_animations.json");
        this.name = name;
    }
}
