package com.nukateam.ntgl.common.foundation.crafting;

import com.nukateam.ntgl.Ntgl;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

/**
 * Author: MrCrayfish
 */

public class ModRecipeType {
    public static final RecipeType<WorkbenchRecipe> WORKBENCH = create("workbench");

    public static void init() {
        // Triggers class initialization and registers the recipe type.
    }

    private static <T extends Recipe<?>> RecipeType<T> create(String name) {
        return Registry.register(BuiltInRegistries.RECIPE_TYPE, new ResourceLocation(Ntgl.MOD_ID, name), new RecipeType<T>() {
            @Override
            public String toString() {
                return name;
            }
        });
    }
}
