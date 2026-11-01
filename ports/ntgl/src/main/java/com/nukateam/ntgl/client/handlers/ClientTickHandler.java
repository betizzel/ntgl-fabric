
package com.nukateam.ntgl.client.handlers;

import com.nukateam.geo.render.ItemAnimator;
import net.minecraft.client.Minecraft;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ClientTickHandler {
    private static final Map<ItemAnimator, Runnable> tickingAnimators = new HashMap<>();
    private static final Map<Object, Consumer<Boolean>> tickers = new HashMap<>();

    public ClientTickHandler() {}

    public static void addTicker(ItemAnimator animator, Runnable onTick) {
        tickingAnimators.put(animator, onTick);
    }

    public static void addTicker(Object object, Consumer<Boolean> onTick) {
        tickers.put(object, onTick);
    }

    public static void startTick(Minecraft client) {
        tickingAnimators.forEach((k, v) -> v.run());
        tickers.forEach((k, v) -> v.accept(true));
    }

    public static void endTick(Minecraft client) {
        tickers.forEach((k, v) -> v.accept(false));
    }
}