package com.nukateam.ntgl.common.network;

import com.nukateam.chassis_core.common.network.packet.*;
import com.nukateam.ntgl.Ntgl;
import com.nukateam.ntgl.common.data.holders.AnimationType;
import com.nukateam.ntgl.common.network.message.weapon.*;
import com.nukateam.ntgl.modules.data.message.S2CMessageUpdateEntityData;
import com.nukateam.ntgl.modules.network.ClientNetwork;
import com.nukateam.ntgl.modules.network.FabricNetwork;
import com.nukateam.ntgl.modules.network.IMessage;
import com.nukateam.ntgl.modules.network.LevelLocation;
import com.nukateam.ntgl.modules.network.EntitySpawnData;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.EntityTrackingEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public final class PacketHandler {
    private static final FabricNetwork PLAY_CHANNEL = new FabricNetwork();
    public static final ResourceLocation ENTITY_SPAWN_DATA = new ResourceLocation(Ntgl.MOD_ID, "entity_spawn_data");
    private static final Map<Class<?>, Entry<?>> ENTRIES = new LinkedHashMap<>();

    private record Entry<T extends IMessage<T>>(ResourceLocation id, T codec, boolean serverbound) {}

    static {
        register(new C2SActionPacket(), true);
        register(new C2SGenericPacket(), true);
        register(new S2CInventoryPacket(), false);
        register(new S2CMessageUpdateChassisConfig(), false);
        register(new S2CMessageUpdateEquipmentConfig(), false);
        register(new C2SMessageAim(), true);
        register(new C2SMessageReload(), true);
        register(new C2SMessageReloadStop(), true);
        register(new C2SMessageShoot(), true);
        register(new C2SMessageUnload(), true);
        register(new C2SMessageCraft(), true);
        register(new C2SMessageAttachments(), true);
        register(new C2SMessageShooting(), true);
        register(new C2SMessagePreFireSound(), true);
        register(new C2SMessageHandAction(), true);
        register(new C2SMessageChangeAmmo(), true);
        register(new C2SMessageMeleeAttack(), true);
        register(new C2SMessageGrenade(), true);
        register(new S2CMessagePlayerAnimation(), false);
        register(new S2CMessageEntityDeathFx(), false);
        register(new S2CMessageStunGrenade(), false);
        register(new S2CMessageUpdateWeapons(), false);
        register(new S2CMessageUpdateAmmo(), false);
        register(new S2CMessageUpdateProjectiles(), false);
        register(new S2CMessageUpdateAttachments(), false);
        register(new S2CMessageBlood(), false);
        register(new S2CMessageGunSound(), false);
        register(new S2CMessageProjectileHitBlock(), false);
        register(new S2CMessageProjectileHitEntity(), false);
        register(new S2CMessageProjectileHitFluid(), false);
        register(new S2CMessageProjectileExplosion(), false);
        register(new S2CMessageUpdateEntityData(), false);
    }

    private static <T extends IMessage<T>> void register(T codec, boolean serverbound) {
        ResourceLocation id = new ResourceLocation(Ntgl.MOD_ID, codec.getClass().getSimpleName().toLowerCase(Locale.ROOT));
        ENTRIES.put(codec.getClass(), new Entry<>(id, codec, serverbound));
    }

    public static FabricNetwork getPlayChannel() { return PLAY_CHANNEL; }

    public static void init() {
        ENTRIES.values().stream().filter(Entry::serverbound).forEach(PacketHandler::registerServer);
        ServerLifecycleEvents.SERVER_STARTED.register(PLAY_CHANNEL::setServer);
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> PLAY_CHANNEL.setServer(null));
        EntityTrackingEvents.START_TRACKING.register((entity, player) -> {
            if (entity instanceof EntitySpawnData data) {
                FriendlyByteBuf buf = PacketByteBufs.create();
                buf.writeVarInt(entity.getId());
                buf.writeUUID(entity.getUUID());
                data.writeSpawnData(buf);
                ServerPlayNetworking.send(player, ENTITY_SPAWN_DATA, buf);
            }
        });
    }

    public static void initClient() {
        ENTRIES.values().stream().filter(entry -> !entry.serverbound()).forEach(PacketHandler::registerClient);
        ClientNetwork.initSpawnData();
    }

    private static <T extends IMessage<T>> void registerServer(Entry<T> entry) {
        ServerPlayNetworking.registerGlobalReceiver(entry.id(), (server, player, handler, buf, responseSender) -> {
            T message = entry.codec().decode(buf);
            server.execute(() -> entry.codec().handle(message, player));
        });
    }

    private static <T extends IMessage<T>> void registerClient(Entry<T> entry) {
        ClientNetwork.register(entry.id(), entry.codec());
    }

    public static ResourceLocation id(IMessage<?> message) {
        Entry<?> entry = ENTRIES.get(message.getClass());
        if (entry == null) throw new IllegalArgumentException("Unregistered packet " + message.getClass().getName());
        return entry.id();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static FriendlyByteBuf encode(IMessage<?> message) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        ((IMessage) message).encode(message, buf);
        return buf;
    }

    public static void sendAnimation(LivingEntity entity, InteractionHand hand, AnimationType animation) {
        var location = LevelLocation.create(entity.level(), entity.blockPosition());
        getPlayChannel().sendToNearbyPlayers(() -> location,
                new S2CMessagePlayerAnimation(entity.getId(), animation, hand));
    }
}
