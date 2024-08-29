package com.github.jarva.allthearcanistgear;

import com.github.jarva.allthearcanistgear.setup.config.ServerConfig;
import com.github.jarva.allthearcanistgear.setup.registry.AddonSetup;
import com.github.jarva.allthearcanistgear.setup.registry.ArsNouveauRegistry;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

@Mod(AllTheArcanistGear.MODID)
public class AllTheArcanistGear {
    public static final String MODID = "allthearcanistgear";
    private static final Logger LOGGER = LogUtils.getLogger();

    public AllTheArcanistGear(IEventBus modEventBus, ModContainer modContainer) {
        AddonSetup.register(modEventBus);
        modContainer.registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC);

        modEventBus.addListener(this::setup);
    }

    public static ResourceLocation prefix(String path){
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    public void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(ArsNouveauRegistry::postInit);
    }
}
