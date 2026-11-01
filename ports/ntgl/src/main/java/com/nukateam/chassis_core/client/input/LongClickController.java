package com.nukateam.chassis_core.client.input;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import org.lwjgl.glfw.GLFW;

public class LongClickController {
    private final Map<Integer, Integer> keys = new HashMap<>();
    private BiConsumer<Integer, Integer> repeatListener;
    private BiConsumer<Integer, Integer> releaseListener;

    public void setRepeatListener(BiConsumer<Integer, Integer> listener) {
        repeatListener = listener;
    }

    public void setReleaseListener(BiConsumer<Integer, Integer> listener) {
        releaseListener = listener;
    }

    public void onTick() {
        keys.replaceAll((key, ticks) -> {
            int elapsed = ticks + 1;
            if (repeatListener != null) repeatListener.accept(key, elapsed);
            return elapsed;
        });
    }

    public void onInput(int action, int key) {
        if (action == GLFW.GLFW_PRESS) keys.put(key, 0);
        else if (action == GLFW.GLFW_RELEASE) {
            Integer elapsed = keys.remove(key);
            if (elapsed != null && releaseListener != null) releaseListener.accept(key, elapsed);
        }
    }
}
