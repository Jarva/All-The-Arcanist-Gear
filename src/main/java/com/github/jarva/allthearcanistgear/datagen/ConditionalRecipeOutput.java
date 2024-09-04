package com.github.jarva.allthearcanistgear.datagen;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.ICondition;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ConditionalRecipeOutput implements Consumer<FinishedRecipe> {
    private final Consumer<FinishedRecipe> consumer;
    private final List<ICondition> conditions;

    public ConditionalRecipeOutput(Consumer<FinishedRecipe> consumer) {
        this.consumer = consumer;
        this.conditions = new ArrayList<>();
    }

    public ConditionalRecipeOutput(Consumer<FinishedRecipe> consumer, List<ICondition> conditions) {
        this.consumer = consumer;
        this.conditions = conditions;
    }

    public ConditionalRecipeOutput withCondition(ICondition condition) {
        List<ICondition> conditions = new ArrayList<>(this.conditions);
        conditions.add(condition);
        return new ConditionalRecipeOutput(consumer, conditions);
    }

    @Override
    public void accept(FinishedRecipe finishedRecipe) {
        if (!this.conditions.isEmpty()) {
            ConditionalRecipe.Builder builder = ConditionalRecipe.builder();
            for (ICondition condition : this.conditions) {
                builder = builder.addCondition(condition);
            }
            builder.addRecipe(finishedRecipe)
                .generateAdvancement()
                .build(consumer, finishedRecipe.getId());
        } else {
            consumer.accept(finishedRecipe);
        }
    }
}
