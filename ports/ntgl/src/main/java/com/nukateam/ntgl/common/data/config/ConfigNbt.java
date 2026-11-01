package com.nukateam.ntgl.common.data.config;

import net.minecraft.nbt.CompoundTag;

/** NBT representation used by weapon and chassis configuration data. */
public interface ConfigNbt {
    CompoundTag serializeNBT();
    void deserializeNBT(CompoundTag tag);
}
