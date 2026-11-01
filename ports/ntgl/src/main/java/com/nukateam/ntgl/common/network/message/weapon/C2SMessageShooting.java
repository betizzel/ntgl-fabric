package com.nukateam.ntgl.common.network.message.weapon;

import net.minecraft.server.level.ServerPlayer;
import com.nukateam.ntgl.common.foundation.init.ModSyncedDataKeys;
import com.nukateam.ntgl.modules.network.IMessage;
import net.minecraft.network.FriendlyByteBuf;

/**
 * Author: MrCrayfish
 */
public class C2SMessageShooting implements IMessage<C2SMessageShooting> {
    private boolean shooting;

    public C2SMessageShooting() {}

    public C2SMessageShooting(boolean shooting) {
        this.shooting = shooting;
    }

    @Override
    public void encode(C2SMessageShooting message, FriendlyByteBuf buffer) {
        buffer.writeBoolean(message.shooting);
    }

    @Override
    public C2SMessageShooting decode(FriendlyByteBuf buffer) {
        return new C2SMessageShooting(buffer.readBoolean());
    }

    @Override public void handle(C2SMessageShooting message, ServerPlayer supplier) { {
        var player = supplier;
        if (player != null) {
            ModSyncedDataKeys.SHOOTING_RIGHT.setValue(player, message.shooting);
        }
    };
     }
}
