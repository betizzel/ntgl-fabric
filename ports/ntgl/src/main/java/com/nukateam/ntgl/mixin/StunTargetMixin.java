package com.nukateam.ntgl.mixin;

import com.nukateam.ntgl.Config;
import com.nukateam.ntgl.common.foundation.init.ModEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Mob.class)
abstract class StunTargetMixin {
    @ModifyVariable(method = "setTarget", at = @At("HEAD"), argsOnly = true)
    private LivingEntity ntgl$clearBlindedTarget(LivingEntity target) {
        Mob mob = (Mob) (Object) this;
        if (target != null && Config.COMMON.stunGrenades.blind.blindMobs.get()
                && mob.hasEffect(ModEffects.BLINDED)) {
            return null;
        }
        return target;
    }
}
