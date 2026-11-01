package com.nukateam.ntgl.modules.datapack.handlers;

import com.nukateam.chassis_core.common.network.packet.S2CMessageUpdateChassisConfig;
import com.nukateam.chassis_core.common.network.packet.S2CMessageUpdateEquipmentConfig;
import com.nukateam.ntgl.Ntgl;
import com.nukateam.ntgl.common.network.PacketHandler;
import com.nukateam.ntgl.common.network.message.weapon.*;
import com.nukateam.ntgl.modules.datapack.managers.*;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public final class NetworkManagerHandler {
    private NetworkManagerHandler() {}

    public static void register() {
        ResourceManagerHelper helper = ResourceManagerHelper.get(PackType.SERVER_DATA);
        addListener(helper, "weapons", NetworkWeaponManager.listener());
        addListener(helper, "ammo", NetworkAmmoManager.listener());
        addListener(helper, "projectiles", NetworkProjectileManager.listener());
        addListener(helper, "attachments", NetworkAttachmentManager.listener());
        addListener(helper, "chassis", NetworkChassisManager.listener());
        addListener(helper, "equipment", NetworkEquipmentManager.listener());

        ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register((player, joined) -> sync(player));
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> {
            NetworkWeaponManager.onServerStopped();
            NetworkAmmoManager.onServerStopped();
            NetworkProjectileManager.onServerStopped();
            NetworkAttachmentManager.onServerStopped();
            NetworkChassisManager.stop();
            NetworkEquipmentManager.stop();
        });
    }

    private static void addListener(ResourceManagerHelper helper, String name, PreparableReloadListener listener) {
        helper.registerReloadListener(new IdentifiableResourceReloadListener() {
            @Override
            public ResourceLocation getFabricId() {
                return new ResourceLocation(Ntgl.MOD_ID, name);
            }

            @Override
            public CompletableFuture<Void> reload(PreparationBarrier barrier, ResourceManager manager,
                    ProfilerFiller prepareProfiler, ProfilerFiller applyProfiler,
                    Executor prepareExecutor, Executor applyExecutor) {
                return listener.reload(barrier, manager, prepareProfiler, applyProfiler, prepareExecutor, applyExecutor);
            }
        });
    }

    private static void sync(ServerPlayer player) {
        var channel = PacketHandler.getPlayChannel();
        channel.sendToPlayer(() -> player, new S2CMessageUpdateWeapons());
        channel.sendToPlayer(() -> player, new S2CMessageUpdateAmmo());
        channel.sendToPlayer(() -> player, new S2CMessageUpdateProjectiles());
        channel.sendToPlayer(() -> player, new S2CMessageUpdateAttachments());
        channel.sendToPlayer(() -> player, new S2CMessageUpdateChassisConfig());
        channel.sendToPlayer(() -> player, new S2CMessageUpdateEquipmentConfig());
    }
}
