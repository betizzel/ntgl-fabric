package com.nukateam.ntgl.common.util.util;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/** Runs delayed tasks on the server thread at the end of a server tick. */
public class DelayedTask {
    private static MinecraftServer server;
    private static final List<Impl> tasks = new ArrayList<>();

    public static void init() {
        ServerLifecycleEvents.SERVER_STARTED.register(started -> {
            server = started;
            tasks.clear();
        });
        ServerLifecycleEvents.SERVER_STOPPING.register(stopping -> {
            tasks.clear();
            server = null;
        });
        ServerTickEvents.END_SERVER_TICK.register(current -> {
            Iterator<Impl> it = tasks.iterator();
            while (it.hasNext()) {
                Impl task = it.next();
                if (task.executionTick <= current.getTickCount()) {
                    task.runnable.run();
                    it.remove();
                }
            }
        });
    }

    public static void runAfter(int ticks, Runnable run) {
        MinecraftServer current = server;
        if (current == null || !current.isSameThread()) {
            throw new IllegalStateException("Tried to add a delayed task without an active server on the main thread");
        }
        tasks.add(new Impl(current.getTickCount() + ticks, run));
    }

    private record Impl(int executionTick, Runnable runnable) {}
}
