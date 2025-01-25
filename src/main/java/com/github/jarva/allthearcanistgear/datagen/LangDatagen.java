package com.github.jarva.allthearcanistgear.datagen;

import com.github.jarva.allthearcanistgear.AllTheArcanistGear;
import com.github.jarva.allthearcanistgear.common.items.armor.ArcanistArmorSet;
import com.github.jarva.allthearcanistgear.setup.registry.AddonItemRegistry;
import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.api.registry.PerkRegistry;
import com.hollingsworth.arsnouveau.common.items.PerkItem;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

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
            this.add("item.allthearcanistgear." + set.getName() + "_spell_book", localizedName + " Spell Book");
        }

        this.add("item.allthearcanistgear.creative_spell_book", "Creative Spell Book");

        this.add("chat.allthearcanistgear.too_weak", "Breaking this block requires more amplification.");
        this.add("chat.allthearcanistgear.low_tier", "Breaking this block requires a more powerful spell book.");

        add("allthearcanistgear.thread_of", "Thread of %s");

        for (PerkItem i : PerkRegistry.getPerkItemMap().values()) {
            if(i.perk.getRegistryName().getNamespace().equals(AllTheArcanistGear.MODID)) {
                add("allthearcanistgear.perk_desc." + i.perk.getRegistryName().getPath(), i.perk.getLangDescription());
                add("item.allthearcanistgear." + i.perk.getRegistryName().getPath(), i.perk.getLangName());
            }
        }
    }
}
