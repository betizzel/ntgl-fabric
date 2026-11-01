package com.nukateam.ntgl.mixin.ntgl.client;

import com.nukateam.ntgl.client.render.hud.ActionWheelHud;
import com.nukateam.ntgl.client.render.hud.ScopeHud;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {
    @Inject(method = "renderCrosshair", at = @At("HEAD"), cancellable = true)
    private void ntgl$renderCrosshair(GuiGraphics graphics, CallbackInfo ci) {
        var minecraft = net.minecraft.client.Minecraft.getInstance();
        if (com.nukateam.ntgl.client.util.handler.CrosshairHandler.get().onRenderOverlay(graphics, minecraft.getFrameTime())) ci.cancel();
    }

    @Inject(method = "render", at = @At("HEAD"))
    private void ntgl$renderEarlyOverlays(GuiGraphics graphics, float tickDelta, CallbackInfo ci) {
        com.nukateam.ntgl.client.util.handler.AimingHandler.get().onRenderOverlay(tickDelta);
        int width = graphics.guiWidth();
        int height = graphics.guiHeight();
        ScopeHud.SCOPE_HUD.render(graphics, tickDelta, width, height);
        ActionWheelHud.HUD.render(graphics, tickDelta, width, height);
    }
}
