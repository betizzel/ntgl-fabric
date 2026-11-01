package com.nukateam.ntgl.common.util.world;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import java.util.List;

/** Hooks for cancelling projectile explosions and filtering their affected entities. */
public final class ProjectileExplosionEvents {
    private ProjectileExplosionEvents() {}

    public static final Event<Start> START = EventFactory.createArrayBacked(Start.class, listeners -> (level, explosion) -> {
        for (Start listener : listeners) {
            if (!listener.allow(level, explosion)) return false;
        }
        return true;
    });
    public static final Event<Detonate> DETONATE = EventFactory.createArrayBacked(Detonate.class, listeners -> (level, explosion, entities, diameter) -> {
        for (Detonate listener : listeners) listener.onDetonate(level, explosion, entities, diameter);
    });

    @FunctionalInterface
    public interface Start {
        boolean allow(Level level, ProjectileExplosion explosion);
    }

    @FunctionalInterface
    public interface Detonate {
        void onDetonate(Level level, ProjectileExplosion explosion, List<Entity> entities, double diameter);
    }
}
