package com.github.jarva.allthearcanistgear.datagen;

import net.minecraft.data.DataGenerator;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import static com.github.jarva.allthearcanistgear.AllTheArcanistGear.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
public class Setup {

    public static String root = MODID;

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();

        gen.addProvider(event.includeServer(), new PatchouliDatagen(gen));
        gen.addProvider(event.includeServer(), new LangDatagen(gen.getPackOutput(), root, "en_us"));
        gen.addProvider(event.includeServer(), new RecipeDatagen(gen.getPackOutput(), event.getLookupProvider()));
        gen.addProvider(event.includeServer(), new ItemTagDatagen(gen.getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper()));
        gen.addProvider(event.includeServer(), new BlockTagDatagen(gen.getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper()));
        gen.addProvider(event.includeServer(), new EntityTypeTagDatagen(gen.getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper()));
        gen.addProvider(event.includeServer(), new ItemModelDatagen(gen.getPackOutput(), event.getExistingFileHelper()));
    }
}
