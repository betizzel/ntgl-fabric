package com.nukateam.ntgl.modules.network;

import net.minecraft.network.FriendlyByteBuf;

/** Additional entity state sent when a player starts tracking an entity. */
public interface EntitySpawnData {
    void writeSpawnData(FriendlyByteBuf buffer);
    void readSpawnData(FriendlyByteBuf buffer);
}
