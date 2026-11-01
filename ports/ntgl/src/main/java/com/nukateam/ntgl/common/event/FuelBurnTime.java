package com.nukateam.ntgl.common.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.world.item.ItemStack;

/** Stack-aware fuel values used by NTGL ammunition and fuel consumers. */
public final class FuelBurnTime {
    /** Return this value to defer to other listeners and then the Fabric fuel registry. */
    public static final int PASS = -1;

    @FunctionalInterface
    public interface Callback {
        /** Returns a nonnegative burn time, or {@link FuelBurnTime#PASS} to defer. */
        int getBurnTime(ItemStack stack);
    }

    public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, listeners -> stack -> {
        for (var listener : listeners) {
            int result = listener.getBurnTime(stack);
            if (result >= 0) return result;
        }
        return PASS;
    });

    private FuelBurnTime() {}

    public static int get(ItemStack stack) {
        if (stack.isEmpty()) return 0;
        int value = EVENT.invoker().getBurnTime(stack);
        if (value >= 0) return value;
        Integer registered = FuelRegistry.INSTANCE.get(stack.getItem());
        return registered == null ? 0 : registered;
    }
}
