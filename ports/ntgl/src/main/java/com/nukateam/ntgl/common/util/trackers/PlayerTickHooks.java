package com.nukateam.ntgl.common.util.trackers;

import com.nukateam.ntgl.common.handlers.ServerEvent;
import com.nukateam.ntgl.common.util.managers.BoundingBoxManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

/** Player tick hooks called at the beginning and end of the actual player tick. */
public final class PlayerTickHooks {
    private PlayerTickHooks() {}

    public static void start(Player player) {
        if (player.level().isClientSide()) return;
        EquipTracker.onPlayerTick(player);
        MeleeTracker.onPlayerTick(player);
        ReloadTracker.onPlayerTick(player);
        ThrowingTracker.onPlayerTick(player);
    }

    public static void end(Player player) {
        if (!(player instanceof ServerPlayer serverPlayer)) return;
        ServerEvent.onPlayerTick(serverPlayer);
        BoundingBoxManager.onPlayerTick(player);
    }
}
