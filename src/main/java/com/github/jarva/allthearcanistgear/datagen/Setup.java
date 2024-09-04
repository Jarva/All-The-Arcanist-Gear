package com.github.jarva.allthearcanistgear.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.github.jarva.allthearcanistgear.AllTheArcanistGear.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Setup {

    public static String root = MODID;

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();

        gen.addProvider(event.includeServer(), new PatchouliDatagen(gen));
        gen.addProvider(event.includeServer(), new LangDatagen(gen.getPackOutput(), root, "en_us"));
        gen.addProvider(event.includeServer(), new RecipeDatagen(gen.getPackOutput()));
        gen.addProvider(event.includeServer(), new ItemTagDatagen(gen.getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper()));
        gen.addProvider(event.includeServer(), new ItemModelDatagen(gen.getPackOutput(), event.getExistingFileHelper()));
    }
}
