package com.nukateam.ntgl.mixin.ntgl.client;

import com.nukateam.ntgl.client.util.handler.ClientShootingHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Inject(method = "startAttack", at = @At("HEAD"), cancellable = true)
    private void ntgl$weaponAttack(CallbackInfoReturnable<Boolean> cir) {
        if (ClientShootingHandler.get().shouldCancelInteraction(true, InteractionHand.MAIN_HAND)) cir.setReturnValue(false);
    }
}
