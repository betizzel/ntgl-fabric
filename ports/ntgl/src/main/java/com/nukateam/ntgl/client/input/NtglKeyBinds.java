package com.nukateam.ntgl.client.input;

import com.mojang.blaze3d.platform.InputConstants;
import com.nukateam.ntgl.Config;
import com.nukateam.ntgl.Ntgl;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import org.lwjgl.glfw.GLFW;

public class NtglKeyBinds {
    public static final KeyMapping KEY_RELOAD = new KeyMapping("key.ntgl.reload", GLFW.GLFW_KEY_R, "key.categories.ntgl");
    public static final KeyMapping KEY_UNLOAD = new KeyMapping("key.ntgl.unload", GLFW.GLFW_KEY_U, "key.categories.ntgl");
    public static final KeyMapping KEY_ATTACHMENTS = new KeyMapping("key.ntgl.attachments", GLFW.GLFW_KEY_Z, "key.categories.ntgl");
    public static final KeyMapping KEY_INSPECT = new KeyMapping("key.ntgl.inspect", GLFW.GLFW_KEY_I, "key.categories.ntgl");
    public static final KeyMapping KEY_FIRE_SELECT = new KeyMapping("key.ntgl.fire_select", GLFW.GLFW_KEY_B, "key.categories.ntgl");
    public static final KeyMapping KEY_AMMO_SELECT = new KeyMapping("key.ntgl.ammo_select", GLFW.GLFW_KEY_N, "key.categories.ntgl");
    public static final KeyMapping KEY_ADD_ATTACK = new KeyMapping("key.ntgl.melee", GLFW.GLFW_KEY_V, "key.categories.ntgl");
    public static final KeyMapping KEY_ALT_ATTACK = new KeyMapping("key.ntgl.alt", GLFW.GLFW_KEY_LEFT_ALT, "key.categories.ntgl");
    public static final KeyMapping KEY_TIPS = new KeyMapping("key.ntgl.key_tips", GLFW.GLFW_KEY_F4, "key.categories.ntgl");

    public static final KeyMapping KEY_DEBUG_X_ADD = new KeyMapping("key.ntgl.debug_x_add", GLFW.GLFW_KEY_LEFT, "key.categories.debug");
    public static final KeyMapping KEY_DEBUG_X_SUB = new KeyMapping("key.ntgl.debug_x_sub", GLFW.GLFW_KEY_RIGHT, "key.categories.debug");
    public static final KeyMapping KEY_DEBUG_Y_SUB = new KeyMapping("key.ntgl.debug_y_sub", GLFW.GLFW_KEY_UP, "key.categories.debug");
    public static final KeyMapping KEY_DEBUG_Y_ADD = new KeyMapping("key.ntgl.debug_y_add", GLFW.GLFW_KEY_DOWN, "key.categories.debug");
    public static final KeyMapping KEY_DEBUG_Z_ADD = new KeyMapping("key.ntgl.debug_z_add", GLFW.GLFW_KEY_PAGE_UP, "key.categories.debug");
    public static final KeyMapping KEY_DEBUG_Z_SUB = new KeyMapping("key.ntgl.debug_z_sub", GLFW.GLFW_KEY_PAGE_DOWN, "key.categories.debug");
    public static final KeyMapping KEY_DEBUG_ZERO = new KeyMapping("key.ntgl.debug_zero", GLFW.GLFW_KEY_KP_ENTER, "key.categories.debug");
    public static final KeyMapping KEY_DEBUG_SHOW = new KeyMapping("key.ntgl.debug_show", GLFW.GLFW_KEY_KP_MULTIPLY, "key.categories.debug");

    public static final KeyMapping KEY_DEBUG_RX_ADD = new KeyMapping("key.ntgl.debug_rx_add", GLFW.GLFW_KEY_KP_4, "key.categories.debug");
    public static final KeyMapping KEY_DEBUG_RX_SUB = new KeyMapping("key.ntgl.debug_rx_sub", GLFW.GLFW_KEY_KP_1, "key.categories.debug");
    public static final KeyMapping KEY_DEBUG_RY_ADD = new KeyMapping("key.ntgl.debug_ry_add", GLFW.GLFW_KEY_KP_5, "key.categories.debug");
    public static final KeyMapping KEY_DEBUG_RY_SUB = new KeyMapping("key.ntgl.debug_ry_sub", GLFW.GLFW_KEY_KP_2, "key.categories.debug");
    public static final KeyMapping KEY_DEBUG_RZ_ADD = new KeyMapping("key.ntgl.debug_rz_add", GLFW.GLFW_KEY_KP_6, "key.categories.debug");
    public static final KeyMapping KEY_DEBUG_RZ_SUB = new KeyMapping("key.ntgl.debug_rz_sub", GLFW.GLFW_KEY_KP_3, "key.categories.debug");

    public static final KeyMapping KEY_DEBUG_TUNING_MODE = new KeyMapping("key.ntgl.debug_tuning_mode", GLFW.GLFW_KEY_KP_DIVIDE, "key.categories.ntgl");
    public static final KeyMapping LEAVE = new KeyMapping("key.ntgl.leave", GLFW.GLFW_KEY_G, "key.categories.ntgl.armor");


    public static void registerKeyMappings() {
        KeyBindingHelper.registerKeyBinding(KEY_RELOAD);
        KeyBindingHelper.registerKeyBinding(KEY_UNLOAD);
        KeyBindingHelper.registerKeyBinding(KEY_ATTACHMENTS);
        KeyBindingHelper.registerKeyBinding(KEY_INSPECT);
        KeyBindingHelper.registerKeyBinding(KEY_FIRE_SELECT);
        KeyBindingHelper.registerKeyBinding(KEY_AMMO_SELECT);
        KeyBindingHelper.registerKeyBinding(KEY_ADD_ATTACK);
        KeyBindingHelper.registerKeyBinding(KEY_ALT_ATTACK);
        KeyBindingHelper.registerKeyBinding(KEY_TIPS);
        KeyBindingHelper.registerKeyBinding(LEAVE);

        if(Ntgl.isDebugging()){
            registerDebugKeys();
        }
    }

    private static void registerDebugKeys() {
        KeyBindingHelper.registerKeyBinding(KEY_DEBUG_X_ADD);
        KeyBindingHelper.registerKeyBinding(KEY_DEBUG_X_SUB);
        KeyBindingHelper.registerKeyBinding(KEY_DEBUG_Y_ADD);
        KeyBindingHelper.registerKeyBinding(KEY_DEBUG_Y_SUB);
        KeyBindingHelper.registerKeyBinding(KEY_DEBUG_Z_ADD);
        KeyBindingHelper.registerKeyBinding(KEY_DEBUG_Z_SUB);
        KeyBindingHelper.registerKeyBinding(KEY_DEBUG_RX_ADD);
        KeyBindingHelper.registerKeyBinding(KEY_DEBUG_RX_SUB);
        KeyBindingHelper.registerKeyBinding(KEY_DEBUG_RY_ADD);
        KeyBindingHelper.registerKeyBinding(KEY_DEBUG_RY_SUB);
        KeyBindingHelper.registerKeyBinding(KEY_DEBUG_RZ_ADD);
        KeyBindingHelper.registerKeyBinding(KEY_DEBUG_RZ_SUB);
        KeyBindingHelper.registerKeyBinding(KEY_DEBUG_ZERO);
        KeyBindingHelper.registerKeyBinding(KEY_DEBUG_SHOW);
        KeyBindingHelper.registerKeyBinding(KEY_DEBUG_TUNING_MODE);
    }

    public static KeyMapping getAimMapping() {
        Minecraft mc = Minecraft.getInstance();
        return Config.CLIENT.controls.flipControls.get() ? mc.options.keyAttack : mc.options.keyUse;
    }

    public static KeyMapping getShootMapping() {
        Minecraft mc = Minecraft.getInstance();
        return Config.CLIENT.controls.flipControls.get() ? mc.options.keyUse : mc.options.keyAttack;
    }
}
