package com.nukateam.ntgl.common.util.helpers.compatibility;


import com.nukateam.ntgl.Ntgl;
import net.minecraft.world.entity.player.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Method;

/**
 * Author: MrCrayfish
 */
public class PlayerReviveHelper {
    private static Method getBleeding;
    private static Method isBleeding;

    public static boolean initialize(boolean modLoaded) {
        if (!modLoaded) return false;
        try {
            ClassLoader loader = PlayerReviveHelper.class.getClassLoader();
            Class<?> server = Class.forName("team.creative.playerrevive.server.PlayerReviveServer", false, loader);
            Class<?> bleeding = Class.forName("team.creative.playerrevive.cap.Bleeding", false, loader);
            Method get = server.getDeclaredMethod("getBleeding", Player.class);
            Method is = bleeding.getDeclaredMethod("isBleeding");
            if (!Modifier.isPublic(server.getModifiers()) || !Modifier.isPublic(bleeding.getModifiers())
                    || !Modifier.isPublic(get.getModifiers()) || !Modifier.isStatic(get.getModifiers())
                    || !Modifier.isPublic(is.getModifiers()) || Modifier.isStatic(is.getModifiers())
                    || !bleeding.isAssignableFrom(get.getReturnType()) || is.getReturnType() != boolean.class) {
                throw new NoSuchMethodException("PlayerRevive bleeding API has incompatible signatures");
            }
            getBleeding = get;
            isBleeding = is;
            return true;
        } catch (ReflectiveOperationException | LinkageError | SecurityException e) {
            Ntgl.LOGGER.warn("PlayerRevive detected, but its bleeding API is incompatible; NTGL integration is disabled", e);
            return false;
        }
    }

    public static boolean isBleeding(Player player) {
        if (!Ntgl.playerReviveLoaded) return false;
        try {
            return (boolean) isBleeding.invoke(getBleeding.invoke(null, player));
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new IllegalStateException("Unable to query PlayerRevive bleeding state", e);
        }
    }

}
