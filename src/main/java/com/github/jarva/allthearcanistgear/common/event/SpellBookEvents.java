package com.github.jarva.allthearcanistgear.common.event;

import com.github.jarva.allthearcanistgear.AllTheArcanistGear;
import com.github.jarva.allthearcanistgear.common.items.book.AddonSpellBook;
import com.github.jarva.allthearcanistgear.datagen.EntityTypeTagDatagen;
import com.hollingsworth.arsnouveau.api.event.SpellDamageEvent;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = AllTheArcanistGear.MODID, bus = EventBusSubscriber.Bus.GAME)
public class SpellBookEvents {
    @SubscribeEvent
    public static void onSpellDamage(SpellDamageEvent.Pre event) {
        ItemStack casterTool = event.context.getCasterTool();
        if (!event.target.getType().is(EntityTypeTagDatagen.BONUS_DAMAGE)) return;
        if (casterTool.getItem() instanceof AddonSpellBook addonSpellBook) {
            event.damage *= addonSpellBook.getConfig().bonusDamageMultiplier().get();
        }
    }
}
