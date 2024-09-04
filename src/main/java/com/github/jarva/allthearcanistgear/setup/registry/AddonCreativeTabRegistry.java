package com.github.jarva.allthearcanistgear.setup.registry;

import com.github.jarva.allthearcanistgear.AllTheArcanistGear;
import com.hollingsworth.arsnouveau.setup.registry.CreativeTabRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class AddonCreativeTabRegistry {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MOB_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AllTheArcanistGear.MODID);

    public static final RegistryObject<CreativeModeTab> ARCANIST_ARMOR_TAB = CREATIVE_MOB_TABS.register("arcanist_tab", () ->
        CreativeModeTab.builder()
            .withTabsBefore(CreativeTabRegistry.GLYPHS.getKey())
            .title(Component.translatable("tab.allthearcanistgear.armor"))
            .icon(() -> AddonItemRegistry.UNOBTAINIUM.getHat().getDefaultInstance())
            .displayItems((parameters, output) -> AddonItemRegistry.REGISTERED_ITEMS.stream().map(item -> item.get().getDefaultInstance()).forEach(output::accept))
            .build()
    );
}
