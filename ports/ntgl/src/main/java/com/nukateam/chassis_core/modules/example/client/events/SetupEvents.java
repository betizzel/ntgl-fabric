package com.nukateam.chassis_core.modules.example.client.events;

import com.nukateam.chassis_core.modules.example.client.ExampleChassisRenderer;
import com.nukateam.chassis_core.modules.example.common.registery.EntityTypes;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class SetupEvents {
    public static void register() {
        EntityRendererRegistry.register(EntityTypes.EXAMPLE_CHASSIS, ExampleChassisRenderer::new);
    }
}
