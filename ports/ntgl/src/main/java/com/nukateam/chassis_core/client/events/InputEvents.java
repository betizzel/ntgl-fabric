package com.nukateam.chassis_core.client.events;

import com.nukateam.chassis_core.client.utils.KeyUtils;
import com.nukateam.chassis_core.common.input.CommonInputHandler;
import com.nukateam.chassis_core.common.input.KeyAction;
import com.nukateam.chassis_core.common.network.actions.InputAction;
import com.nukateam.ntgl.client.input.NtglKeyBinds;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

import static com.nukateam.chassis_core.common.network.PacketSender.doServerAction;
import static com.nukateam.chassis_core.common.util.helpers.PlayerUtils.getLocalPlayer;
import static com.nukateam.chassis_core.common.util.helpers.PlayerUtils.stopWearingArmor;

public class InputEvents {
    public static void onKeyInput(int key, int action) {
        if (isNotInGame()) return;
        if (action == GLFW.GLFW_PRESS && key == KeyBindingHelper.getBoundKeyOf(NtglKeyBinds.LEAVE).getValue()) {
            stopWearingArmor(Minecraft.getInstance().player);
        }
        handleInput(key, action == GLFW.GLFW_PRESS ? KeyAction.PRESS : action == GLFW.GLFW_RELEASE ? KeyAction.RELEASE : KeyAction.REPEAT);
    }

    public static void onMouseKeyInput(int button, int action) {
        if (action == GLFW.GLFW_RELEASE) {
            var options = Minecraft.getInstance().options;
            if (button != KeyBindingHelper.getBoundKeyOf(options.keyUse).getValue() && isNotInGame()) return;
            handleInput(button, KeyAction.RELEASE);
        }
    }

    public static void onDoubleClick(int key) {
        if (!isNotInGame()) handleInput(key, KeyAction.DOUBLE_CLICK);
    }

    public static void onLongClick(int key, int ticks) {
        if (!isNotInGame()) handleInput(key, KeyAction.LONG_PRESS);
    }

    private static void handleInput(int key, KeyAction action) {
        var inputKey = KeyUtils.getByKey(key);
        if (getLocalPlayer() == null || inputKey == null) return;
        doServerAction(new InputAction(inputKey, action), -1);
        CommonInputHandler.onKeyInput(inputKey, action, getLocalPlayer());
    }

    public static boolean isNotInGame() {
        return Minecraft.getInstance().screen != null;
    }
}
