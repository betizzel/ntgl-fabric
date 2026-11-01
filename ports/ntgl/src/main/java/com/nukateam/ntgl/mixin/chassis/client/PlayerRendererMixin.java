package com.nukateam.ntgl.mixin.chassis.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.nukateam.chassis_core.client.events.PlayerEvents;
import com.nukateam.chassis_core.common.util.helpers.PlayerUtils;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.HumanoidArm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin {
    @Inject(method = "render(Lnet/minecraft/client/player/AbstractClientPlayer;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("HEAD"), cancellable = true)
    private void chassis$hideRider(AbstractClientPlayer player, float yaw, float tickDelta, PoseStack poses,
                                  MultiBufferSource buffers, int light, CallbackInfo ci) {
        if (PlayerUtils.isWearingChassis(player)) ci.cancel();
    }

    @Inject(method = "renderRightHand", at = @At("HEAD"), cancellable = true)
    private void chassis$renderRightHand(PoseStack poses, MultiBufferSource buffers, int light,
                                         AbstractClientPlayer player, CallbackInfo ci) {
        if (PlayerEvents.renderHand(HumanoidArm.RIGHT, poses, buffers, light)) ci.cancel();
    }

    @Inject(method = "renderLeftHand", at = @At("HEAD"), cancellable = true)
    private void chassis$renderLeftHand(PoseStack poses, MultiBufferSource buffers, int light,
                                        AbstractClientPlayer player, CallbackInfo ci) {
        if (PlayerEvents.renderHand(HumanoidArm.LEFT, poses, buffers, light)) ci.cancel();
    }
}
