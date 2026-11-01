package com.nukateam.ntgl.mixin.ntgl.client;

import com.nukateam.ntgl.client.util.handler.SoundHandler;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(SoundEngine.class)
public abstract class SoundEngineMixin {
    @ModifyVariable(method = "play", at = @At("HEAD"), argsOnly = true)
    private SoundInstance ntgl$deafenSound(SoundInstance sound) {
        return SoundHandler.get().lowerInitialVolume((SoundEngine) (Object) this, sound);
    }
}
