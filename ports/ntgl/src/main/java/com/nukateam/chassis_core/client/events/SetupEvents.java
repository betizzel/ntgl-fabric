package com.nukateam.chassis_core.client.events;

import com.nukateam.chassis_core.client.input.DoubleClickController;
import com.nukateam.chassis_core.client.input.LongClickController;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public final class SetupEvents {
    public static final DoubleClickController DOUBLE_CLICK_CONTROLLER = new DoubleClickController();
    private static final LongClickController LONG_CLICK_CONTROLLER = new LongClickController();

    public static void clientSetup() {
        DOUBLE_CLICK_CONTROLLER.addListener(InputEvents::onDoubleClick);
        LONG_CLICK_CONTROLLER.setRepeatListener(InputEvents::onLongClick);
        ClientTickEvents.START_CLIENT_TICK.register(client -> tick());
        ClientTickEvents.END_CLIENT_TICK.register(client -> tick());
    }

    private static void tick() {
        DOUBLE_CLICK_CONTROLLER.onTick();
        LONG_CLICK_CONTROLLER.onTick();
    }

    public static void onKey(int key, int action) {
        InputEvents.onKeyInput(key, action);
        DOUBLE_CLICK_CONTROLLER.onClick(key, action);
        LONG_CLICK_CONTROLLER.onInput(action, key);
    }

    public static void onMouse(int button, int action) {
        InputEvents.onMouseKeyInput(button, action);
        LONG_CLICK_CONTROLLER.onInput(action, button);
    }
}
