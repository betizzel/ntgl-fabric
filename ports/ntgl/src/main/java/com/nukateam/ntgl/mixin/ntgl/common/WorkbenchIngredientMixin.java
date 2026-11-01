package com.nukateam.ntgl.mixin.ntgl.common;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.nukateam.ntgl.common.foundation.crafting.WorkbenchIngredient;
import net.minecraft.world.item.crafting.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Ingredient.class)
public abstract class WorkbenchIngredientMixin {
    @Inject(method = "fromJson(Lcom/google/gson/JsonElement;Z)Lnet/minecraft/world/item/crafting/Ingredient;", at = @At("HEAD"), cancellable = true)
    private static void ntgl$readLegacyWorkbenchIngredient(JsonElement json, boolean allowEmpty,
            CallbackInfoReturnable<Ingredient> callback) {
        if (json == null || !json.isJsonObject()) return;
        JsonObject object = json.getAsJsonObject();
        JsonElement type = object.get("type");
        if (!object.has("fabric:type") && type != null && type.isJsonPrimitive()
                && type.getAsJsonPrimitive().isString() && "ntgl:workbench_ingredient".equals(type.getAsString())) {
            callback.setReturnValue(WorkbenchIngredient.fromJson(object).toVanilla());
        }
    }
}
