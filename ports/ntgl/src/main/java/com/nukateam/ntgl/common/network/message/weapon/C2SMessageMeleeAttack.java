package com.nukateam.ntgl.common.network.message.weapon;

import net.minecraft.server.level.ServerPlayer;
import com.nukateam.ntgl.modules.network.IMessage;
import com.nukateam.ntgl.common.data.holders.WeaponMode;
import com.nukateam.ntgl.common.network.ServerPlayHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;

public class C2SMessageMeleeAttack implements IMessage<C2SMessageMeleeAttack> {
    InteractionHand hand;
    WeaponMode action;

    public C2SMessageMeleeAttack() {}

    public C2SMessageMeleeAttack(InteractionHand hand, WeaponMode action) {
        this.hand = hand;
        this.action = action;
    }

    @Override
    public void encode(C2SMessageMeleeAttack message, FriendlyByteBuf buffer) {
        buffer.writeEnum(message.hand);
        buffer.writeUtf(message.action.toString());
    }

    @Override
    public C2SMessageMeleeAttack decode(FriendlyByteBuf buffer) {
        return new C2SMessageMeleeAttack(
                buffer.readEnum(InteractionHand.class),
                WeaponMode.getType(buffer.readUtf())
        );
    }

    @Override public void handle(C2SMessageMeleeAttack message, ServerPlayer context) { {
        var player = context;
        if (player != null) {
            ServerPlayHandler.handleMeleeAttack(message, player);
        }
    };
     }

    public InteractionHand getHand() {
        return hand;
    }

    public WeaponMode getAction() {
        return action;
    }
}
