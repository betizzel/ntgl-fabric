package com.nukateam.chassis_core.client.utils;

import com.nukateam.chassis_core.common.input.InputKey;
import com.nukateam.ntgl.client.input.NtglKeyBinds;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

public class KeyUtils {
    private static boolean matches(KeyMapping mapping, int key) {
        return KeyBindingHelper.getBoundKeyOf(mapping).getValue() == key;
    }

    public static InputKey getByKey(int key) {
        var options = Minecraft.getInstance().options;
        if (matches(options.keyUp, key)) return InputKey.UP;
        if (matches(options.keyDown, key)) return InputKey.DOWN;
        if (matches(options.keyLeft, key)) return InputKey.LEFT;
        if (matches(options.keyRight, key)) return InputKey.RIGHT;
        if (matches(options.keyJump, key)) return InputKey.JUMP;
        if (matches(NtglKeyBinds.LEAVE, key)) return InputKey.LEAVE;
        if (matches(options.keyUse, key)) return InputKey.USE;
        if (matches(options.keyAttack, key)) return InputKey.ATTACK;
        return null;
    }
}
