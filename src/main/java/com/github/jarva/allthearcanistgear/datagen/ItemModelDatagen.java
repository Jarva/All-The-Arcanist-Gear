package com.github.jarva.allthearcanistgear.datagen;

import com.github.jarva.allthearcanistgear.AllTheArcanistGear;
import com.github.jarva.allthearcanistgear.setup.registry.AddonItemRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ItemModelDatagen extends ItemModelProvider {
    public ItemModelDatagen(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, AllTheArcanistGear.MODID, exFileHelper);
    }

    @Override
    protected void registerModels() {
        for (RegistryObject<? extends Item> item : AddonItemRegistry.DATAGEN_ITEMS) {
            basicItem(item.get());
        }
    }
}
