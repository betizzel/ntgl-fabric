package com.nukateam.ntgl.common.handlers;

import com.nukateam.ntgl.Ntgl;
import com.nukateam.ntgl.common.data.WeaponData;
import com.nukateam.ntgl.common.foundation.init.ModSyncedDataKeys;
import com.nukateam.ntgl.common.util.util.WeaponStateHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;

import static com.nukateam.ntgl.common.util.trackers.ShootTracker.*;
import static com.nukateam.ntgl.common.network.ServerPlayHandler.*;
import static com.nukateam.ntgl.common.util.util.WeaponModifierHelper.*;

public class ServerEvent {
    public static void onPlayerTick(ServerPlayer player) {
        handleAutoReload(player, InteractionHand.MAIN_HAND);
        handleAutoReload(player, InteractionHand.OFF_HAND);
    }

    private static void handleAutoReload(ServerPlayer player, InteractionHand hand) {
        var stack = player.getItemInHand(hand);
        var shootTracker = getShootTracker(player, hand);
        var isReloading = ModSyncedDataKeys.getReloadKey(hand).getValue(player);
        var data = new WeaponData(stack, player);

        if (!player.isCreative()
                && isGun(stack)
                && !isReloading
                && isAutoReloading(data)
                && shootTracker.cooldownEnded()
                && !WeaponStateHelper.hasAmmo(data)) {
            reloadGun(hand, data);
        }
    }
}
