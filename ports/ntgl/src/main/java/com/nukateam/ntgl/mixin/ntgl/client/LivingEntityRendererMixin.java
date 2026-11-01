package com.nukateam.ntgl.mixin.ntgl.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.nukateam.ntgl.ClientProxy;
import com.nukateam.ntgl.common.data.enums.DeathType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin {
    @Inject(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("HEAD"), cancellable = true)
    private void ntgl$hideDeathEffectEntity(LivingEntity entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource buffers, int light, CallbackInfo ci) {
        DeathType type = ClientProxy.getDamageType(entity);
        if (type == DeathType.LASER || type == DeathType.FIRE || type == DeathType.GORE) {
            ci.cancel();
            return;
        }
        com.nukateam.ntgl.client.util.handler.EntityModelHandler.onRenderEntityPre(entity, tickDelta, matrices, buffers);
    }
}
