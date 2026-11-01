package com.nukateam.ntgl.modules.network;

import com.nukateam.ntgl.common.network.PacketHandler;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.SectionPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.Vec3;

import java.util.function.Supplier;

public final class FabricNetwork {
    private MinecraftServer server;

    public void setServer(MinecraftServer server) { this.server = server; }

    public void sendToPlayer(Supplier<ServerPlayer> supplier, IMessage<?> message) {
        ServerPlayer player = supplier.get();
        ServerPlayNetworking.send(player, PacketHandler.id(message), PacketHandler.encode(message));
    }

    public void sendToTrackingEntity(Supplier<Entity> supplier, IMessage<?> message) {
        for (ServerPlayer player : PlayerLookup.tracking(supplier.get())) sendToPlayer(() -> player, message);
    }

    public void sendToTrackingBlockEntity(Supplier<BlockEntity> supplier, IMessage<?> message) {
        BlockEntity entity = supplier.get();
        sendToTrackingChunk(() -> entity.getLevel().getChunkAt(entity.getBlockPos()), message);
    }

    public void sendToTrackingLocation(Supplier<LevelLocation> supplier, IMessage<?> message) {
        LevelLocation location = supplier.get();
        Vec3 pos = location.pos();
        sendToTrackingChunk(() -> location.level().getChunk(SectionPos.blockToSectionCoord(pos.x), SectionPos.blockToSectionCoord(pos.z)), message);
    }

    public void sendToTrackingChunk(Supplier<LevelChunk> supplier, IMessage<?> message) {
        LevelChunk chunk = supplier.get();
        for (ServerPlayer player : PlayerLookup.tracking((ServerLevel) chunk.getLevel(), chunk.getPos())) sendToPlayer(() -> player, message);
    }

    public void sendToNearbyPlayers(Supplier<LevelLocation> supplier, IMessage<?> message) {
        LevelLocation location = supplier.get();
        Vec3 pos = location.pos();
        double rangeSquared = location.range() * location.range();
        for (ServerPlayer player : ((ServerLevel) location.level()).players()) {
            if (player.distanceToSqr(pos.x, pos.y, pos.z) < rangeSquared) sendToPlayer(() -> player, message);
        }
    }

    public void sendToServer(IMessage<?> message) {
        ClientNetwork.sendToServer(message);
    }

    public void sendToAll(IMessage<?> message) {
        if (server == null) throw new IllegalStateException("Server not running");
        for (ServerPlayer player : server.getPlayerList().getPlayers()) sendToPlayer(() -> player, message);
    }
}
