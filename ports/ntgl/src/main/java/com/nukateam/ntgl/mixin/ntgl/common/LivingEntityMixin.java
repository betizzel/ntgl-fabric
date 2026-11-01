package com.nukateam.ntgl.mixin.ntgl.common;

import com.nukateam.ntgl.Config;
import com.nukateam.ntgl.common.foundation.entity.ProjectileEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Author: MrCrayfish
 */
@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Unique private net.minecraft.world.item.ItemStack ntgl$lastMainHand = net.minecraft.world.item.ItemStack.EMPTY;
    @Unique private net.minecraft.world.item.ItemStack ntgl$lastOffHand = net.minecraft.world.item.ItemStack.EMPTY;

    @Inject(method = "tick", at = @At("TAIL"))
    private void ntgl$tickEquipmentAndWeapons(CallbackInfo ci) {
        var entity = (LivingEntity) (Object) this;
        com.nukateam.ntgl.common.handlers.EntityEvents.onLivingTick(entity);
        com.nukateam.ntgl.common.handlers.GunBehaviorManager.onLivingTick(entity);
        var main = entity.getMainHandItem();
        if (!net.minecraft.world.item.ItemStack.matches(ntgl$lastMainHand, main)) {
            com.nukateam.ntgl.common.handlers.PlayerEventHandler.onChangeEquipment(entity, net.minecraft.world.entity.EquipmentSlot.MAINHAND, ntgl$lastMainHand, main);
            ntgl$lastMainHand = main.copy();
        }
        var off = entity.getOffhandItem();
        if (!net.minecraft.world.item.ItemStack.matches(ntgl$lastOffHand, off)) {
            com.nukateam.ntgl.common.handlers.PlayerEventHandler.onChangeEquipment(entity, net.minecraft.world.entity.EquipmentSlot.OFFHAND, ntgl$lastOffHand, off);
            ntgl$lastOffHand = off.copy();
        }
    }

    @Inject(method = "swing(Lnet/minecraft/world/InteractionHand;Z)V", at = @At("HEAD"), cancellable = true)
    private void ntgl$weaponSwing(net.minecraft.world.InteractionHand hand, boolean updateSelf, CallbackInfo ci) {
        var entity = (LivingEntity) (Object) this;
        var stack = entity.getItemInHand(hand);
        if (stack.getItem() instanceof com.nukateam.ntgl.common.foundation.item.WeaponItem weapon && weapon.onEntitySwing(stack, entity)) ci.cancel();
    }

    private DamageSource source;

    @Inject(method = "hurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;knockback(DDD)V"))
    private void capture(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        this.source = source;
    }

    @ModifyArg(method = "hurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;knockback(DDD)V"), index = 0)
    private double modifyApplyKnockbackArgs(double original) {
        if (this.source.getDirectEntity() instanceof ProjectileEntity) {
            if (!Config.COMMON.gameplay.enableKnockback.get()) {
                return 0;
            }

            double strength = Config.COMMON.gameplay.knockbackStrength.get();
            if (strength > 0) {
                return strength;
            }
        }
        return original;
    }
}