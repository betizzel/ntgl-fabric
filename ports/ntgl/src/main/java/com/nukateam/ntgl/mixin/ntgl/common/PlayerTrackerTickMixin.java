package com.nukateam.ntgl.mixin.ntgl.common;

import com.nukateam.ntgl.common.util.trackers.PlayerTickHooks;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerTrackerTickMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    private void ntgl$startPlayerTrackers(CallbackInfo ci) {
        PlayerTickHooks.start((Player) (Object) this);
    }

    @Inject(method = "tick", at = @At("RETURN"))
    private void ntgl$endPlayerTrackers(CallbackInfo ci) {
        PlayerTickHooks.end((Player) (Object) this);
    }
}
