package com.nukateam.chassis_core.client.input;

import java.util.function.IntConsumer;
import org.lwjgl.glfw.GLFW;

public class DoubleClickController {
    private static final int MAX_TICKS = 10;
    private Integer lastKey;
    private int ticks = MAX_TICKS;
    private IntConsumer listener;

    public void addListener(IntConsumer listener) {
        this.listener = listener;
    }

    public void onTick() {
        if (lastKey != null && --ticks <= 0) lastKey = null;
    }

    public void onClick(int key, int action) {
        if (action != GLFW.GLFW_PRESS) return;
        if (lastKey == null || lastKey != key) {
            lastKey = key;
            ticks = MAX_TICKS;
        } else if (ticks > 0) {
            lastKey = null;
            if (listener != null) listener.accept(key);
        }
    }
}
