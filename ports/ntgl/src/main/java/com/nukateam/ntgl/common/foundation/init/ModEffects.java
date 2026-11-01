package com.nukateam.ntgl.common.foundation.init;

import com.nukateam.ntgl.common.foundation.effect.IncurableEffect;
import com.nukateam.ntgl.Ntgl;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

/**
 * Author: MrCrayfish
 */
public class ModEffects {
    public static final IncurableEffect BLINDED = Registry.register(BuiltInRegistries.MOB_EFFECT, new ResourceLocation(Ntgl.MOD_ID, "blinded"), new IncurableEffect(MobEffectCategory.HARMFUL, 0));
    public static final IncurableEffect DEAFENED = Registry.register(BuiltInRegistries.MOB_EFFECT, new ResourceLocation(Ntgl.MOD_ID, "deafened"), new IncurableEffect(MobEffectCategory.HARMFUL, 0));

    public static void init() {
        // Triggers class initialization and registers effects.
    }
}
