package com.nukateam.ntgl.common.foundation.effect;

import io.github.fabricators_of_create.porting_lib.entity.events.living.MobEffectEvent;

public final class IncurableMilk {
    private static final ThreadLocal<Boolean> ACTIVE = new ThreadLocal<>();

    private IncurableMilk() {}

    public static boolean enter() {
        boolean previous = Boolean.TRUE.equals(ACTIVE.get());
        ACTIVE.set(true);
        return previous;
    }

    public static void restore(boolean previous) {
        if (previous) ACTIVE.set(true);
        else ACTIVE.remove();
    }

    public static void register() {
        MobEffectEvent.REMOVE.register(event -> {
            if (Boolean.TRUE.equals(ACTIVE.get()) && event.getEffectInstance() != null
                    && event.getEffectInstance().getEffect() instanceof IncurableEffect) {
                event.setCanceled(true);
            }
        });
    }
}
