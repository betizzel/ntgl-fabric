package com.nukateam.chassis_core.common.network.actions;

import com.nukateam.chassis_core.common.network.ActionRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public abstract class Action<T extends Action<T>> {
    public int getId() {
        return ActionRegistry.getActionId(this.getClass());
    }

    public String getName() {
        return this.getClass().getCanonicalName();
    }

    public abstract void doServerAction(T message, ServerPlayer player, int entityId);

    public abstract void write(FriendlyByteBuf buffer);

    public abstract T read(FriendlyByteBuf buffer);
}
