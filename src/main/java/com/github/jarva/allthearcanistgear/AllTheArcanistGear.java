package com.github.jarva.allthearcanistgear;

import com.github.jarva.allthearcanistgear.setup.config.ServerConfig;
import com.github.jarva.allthearcanistgear.setup.registry.AddonSetup;
import com.github.jarva.allthearcanistgear.setup.registry.ArsNouveauRegistry;
import com.hollingsworth.arsnouveau.setup.config.ANModConfig;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(AllTheArcanistGear.MODID)
public class AllTheArcanistGear {
    public static final String MODID = "allthearcanistgear";
    private static final Logger LOGGER = LogUtils.getLogger();

    public AllTheArcanistGear() {
        IEventBus modbus = FMLJavaModLoadingContext.get().getModEventBus();
        AddonSetup.register(modbus);
        ANModConfig serverConfig = new ANModConfig(ModConfig.Type.SERVER, ServerConfig.SPEC, ModLoadingContext.get().getActiveContainer(), MODID + "-server");
        ModLoadingContext.get().getActiveContainer().addConfig(serverConfig);

        modbus.addListener(this::setup);
    }

    public static ResourceLocation prefix(String path){
        return new ResourceLocation(MODID, path);
    }

    public void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(ArsNouveauRegistry::postInit);
        event.enqueueWork(AddonSetup::registerIngredients);
    }
}
