package com.nukateam.ntgl.common.foundation.init;

import com.nukateam.ntgl.Ntgl;
import com.nukateam.ntgl.common.foundation.crafting.WorkbenchRecipeSerializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class ModRecipeSerializers {
    public static final WorkbenchRecipeSerializer WORKBENCH = Registry.register(
            BuiltInRegistries.RECIPE_SERIALIZER, new ResourceLocation(Ntgl.MOD_ID, "workbench"),
            new WorkbenchRecipeSerializer());

    public static void init() {}
}
