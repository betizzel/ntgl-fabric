package com.nukateam.ntgl.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.nukateam.ntgl.common.foundation.effect.IncurableMilk;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.MilkBucketItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MilkBucketItem.class)
abstract class IncurableMilkMixin {
    @WrapOperation(method = "finishUsingItem", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;removeAllEffects()Z"))
    private boolean ntgl$removeCurableEffects(LivingEntity entity, Operation<Boolean> original) {
        boolean previous = IncurableMilk.enter();
        try {
            return original.call(entity);
        } finally {
            IncurableMilk.restore(previous);
        }
    }
}
