package com.nukateam.ntgl.mixin.chassis.client;

import com.nukateam.chassis_core.common.util.helpers.PlayerUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Gui.class)
public abstract class ForgeIngameGuiMixin {
    @Inject(method = "getPlayerVehicleWithHealth", at = @At("HEAD"), cancellable = true)
    private void ntgl$hideChassisVehicleHealth(CallbackInfoReturnable<LivingEntity> cir) {
        if (PlayerUtils.isLocalWearingChassis()) cir.setReturnValue(null);
    }
}
