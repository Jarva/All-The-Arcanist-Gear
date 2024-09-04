package com.github.jarva.allthearcanistgear.datagen;

import com.github.jarva.allthearcanistgear.common.armor.ArcanistArmorSet;
import com.github.jarva.allthearcanistgear.setup.registry.AddonItemRegistry;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class LangDatagen extends LanguageProvider {

    public LangDatagen(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    protected void addTranslations() {
        this.add("tab.allthearcanistgear.armor", "All The Arcanist Gear");

        for (ArcanistArmorSet set : AddonItemRegistry.ARMOR_SETS) {
            String localizedName = set.getName().substring(0, 1).toUpperCase() + set.getName().substring(1);
            this.add("item.allthearcanistgear." + set.getName() + "_hat", localizedName + " Arcanist Hat");
            this.add("item.allthearcanistgear." + set.getName() + "_robes", localizedName + " Arcanist Robes");
            this.add("item.allthearcanistgear." + set.getName() + "_leggings", localizedName + " Arcanist Leggings");
            this.add("item.allthearcanistgear." + set.getName() + "_boots", localizedName + " Arcanist Boots");
        }
    }
}
