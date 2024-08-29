package com.github.jarva.allthearcanistgear.datagen;

import com.github.jarva.allthearcanistgear.AllTheArcanistGear;
import com.github.jarva.allthearcanistgear.setup.registry.AddonItemRegistry;
import com.hollingsworth.arsnouveau.setup.registry.ItemRegistryWrapper;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ItemModelDatagen extends ItemModelProvider {
    public ItemModelDatagen(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, AllTheArcanistGear.MODID, exFileHelper);
    }

    @Override
    protected void registerModels() {
        for (ItemRegistryWrapper<? extends Item> item : AddonItemRegistry.DATAGEN_ITEMS) {
            basicItem(item.get());
        }
    }
}
