package com.nukateam.example.client;

import com.nukateam.example.common.registery.EntityTypes;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class SetupEvents {
    public static void register() {
        EntityRendererRegistry.register(EntityTypes.RAIDER, RaiderRenderer::new);
        EntityRendererRegistry.register(EntityTypes.BRAHMIN, context -> new SimpleEntityRenderer<>(context, new BrahminModel()));
    }
}
