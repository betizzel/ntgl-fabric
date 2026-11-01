package com.nukateam.ntgl.modules.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import javax.annotation.Nullable;

public interface IMessage<T> {
    void encode(T message, FriendlyByteBuf buffer);
    T decode(FriendlyByteBuf buffer);
    void handle(T message, @Nullable ServerPlayer sender);
}
