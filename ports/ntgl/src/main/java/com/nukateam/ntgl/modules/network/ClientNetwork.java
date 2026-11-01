package com.nukateam.ntgl.modules.network;

import com.nukateam.ntgl.common.network.PacketHandler;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import io.netty.buffer.Unpooled;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.resources.ResourceLocation;

public final class ClientNetwork {
    private static final Map<UUID, byte[]> SPAWN_DATA = new HashMap<>();
    private ClientNetwork() {}

    public static void initSpawnData() {
        ClientPlayNetworking.registerGlobalReceiver(PacketHandler.ENTITY_SPAWN_DATA, (client, handler, buf, sender) -> {
            int entityId = buf.readVarInt();
            UUID uuid = buf.readUUID();
            byte[] bytes = new byte[buf.readableBytes()];
            buf.readBytes(bytes);
            client.execute(() -> {
                Entity entity = client.level == null ? null : client.level.getEntity(entityId);
                if (entity instanceof EntitySpawnData data && entity.getUUID().equals(uuid)) {
                    applySpawnData(data, bytes);
                } else {
                    SPAWN_DATA.put(uuid, bytes);
                }
            });
        });
        ClientEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (entity instanceof EntitySpawnData data) {
                byte[] bytes = SPAWN_DATA.remove(entity.getUUID());
                if (bytes != null) applySpawnData(data, bytes);
            }
        });
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> SPAWN_DATA.clear());
    }

    private static void applySpawnData(EntitySpawnData data, byte[] bytes) {
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.wrappedBuffer(bytes));
        try {
            data.readSpawnData(buf);
        } finally {
            buf.release();
        }
    }

    public static <T extends IMessage<T>> void register(ResourceLocation id, T codec) {
        ClientPlayNetworking.registerGlobalReceiver(id, (client, handler, buf, responseSender) -> {
            T message = codec.decode(buf);
            client.execute(() -> codec.handle(message, null));
        });
    }

    public static void sendToServer(IMessage<?> message) {
        ClientPlayNetworking.send(PacketHandler.id(message), PacketHandler.encode(message));
    }
}
