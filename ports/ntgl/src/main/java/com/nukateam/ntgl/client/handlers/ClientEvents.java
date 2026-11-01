package com.nukateam.ntgl.client.handlers;

import com.nukateam.ntgl.client.render.hud.DebugHud;
import com.nukateam.ntgl.client.render.hud.WeaponHud;
import com.nukateam.ntgl.client.tooltip.ItemsClientTooltipComponent;
import com.nukateam.ntgl.client.tooltip.ItemsTooltipData;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;

public final class ClientEvents {
    public static void register() {
        TooltipComponentCallback.EVENT.register(data -> data instanceof ItemsTooltipData items
                ? new ItemsClientTooltipComponent(items) : null);
        HudRenderCallback.EVENT.register((graphics, tickDelta) -> {
            int width = graphics.guiWidth();
            int height = graphics.guiHeight();
            WeaponHud.AMMO_HUD.render(graphics, tickDelta, width, height);
            DebugHud.render(graphics, tickDelta, width, height);
        });
    }
}
