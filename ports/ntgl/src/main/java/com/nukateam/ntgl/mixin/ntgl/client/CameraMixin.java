package com.nukateam.ntgl.mixin.ntgl.client;

import com.nukateam.ntgl.client.util.handler.WeaponRenderingHandler;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class CameraMixin {
    @Shadow @Final private Quaternionf rotation;

    @Inject(method = "setup", at = @At("TAIL"))
    private void ntgl$rollCamera(BlockGetter level, Entity entity, boolean detached, boolean inverse, float tickDelta, CallbackInfo ci) {
        rotation.rotateZ((float) Math.toRadians(WeaponRenderingHandler.get().getCameraRoll(tickDelta)));
    }
}
