package com.nukateam.chassis_core.common.events;

import com.nukateam.chassis_core.common.input.InputKey;
import com.nukateam.chassis_core.common.input.KeyAction;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.player.Player;

@FunctionalInterface
public interface CommonInputEvent {
    Event<CommonInputEvent> EVENT = EventFactory.createArrayBacked(CommonInputEvent.class,
            listeners -> (key, action, player) -> {
                for (var listener : listeners) listener.onInput(key, action, player);
            });

    void onInput(InputKey key, KeyAction action, Player player);
}
