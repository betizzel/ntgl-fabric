package com.nukateam.ntgl.common.foundation.init;

import com.nukateam.ntgl.Ntgl;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class NtglGameEvents {
    public static final ResourceKey<GameEvent> GUNSHOT_EVENT =
            ResourceKey.create(Registries.GAME_EVENT, new ResourceLocation(Ntgl.MOD_ID, "gunshot_event"));
    private static final GameEvent GUNSHOT = Registry.register(BuiltInRegistries.GAME_EVENT,
            GUNSHOT_EVENT.location(), new GameEvent("gunshot_event", 32));

    public static void init() {}

    public static void gunshotEvent(Level level, LivingEntity entity) {
        level.gameEvent(entity, GUNSHOT, entity.blockPosition());
    }
}
