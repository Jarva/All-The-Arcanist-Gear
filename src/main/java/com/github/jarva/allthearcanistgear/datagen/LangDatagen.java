package com.github.jarva.allthearcanistgear.datagen;

import com.github.jarva.allthearcanistgear.common.armor.ArmorSet;
import com.github.jarva.allthearcanistgear.setup.registry.AddonItemRegistry;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class LangDatagen extends LanguageProvider {

    public LangDatagen(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    protected void addTranslations() {
        this.add("tab.allthearcanistgear.armor", "All The Arcanist Gear");

        for (ArmorSet set : AddonItemRegistry.ARMOR_SETS) {
            String localizedName = set.getName().substring(0, 1).toUpperCase() + set.getName().substring(1);
            this.add("item.allthearcanistgear." + set.getName() + "_hat", localizedName + " Arcanist's Hat");
            this.add("item.allthearcanistgear." + set.getName() + "_robes", localizedName + " Arcanist's Robes");
            this.add("item.allthearcanistgear." + set.getName() + "_leggings", localizedName + " Arcanist's Leggings");
            this.add("item.allthearcanistgear." + set.getName() + "_boots", localizedName + " Arcanist's Boots");
        }
    }
}
