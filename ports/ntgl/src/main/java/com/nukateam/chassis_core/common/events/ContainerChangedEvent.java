package com.nukateam.chassis_core.common.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.Entity;

@FunctionalInterface
public interface ContainerChangedEvent {
    Event<ContainerChangedEvent> EVENT = EventFactory.createArrayBacked(ContainerChangedEvent.class,
            listeners -> entity -> {
                for (var listener : listeners) listener.onContainerChanged(entity);
            });

    void onContainerChanged(Entity entity);
}
