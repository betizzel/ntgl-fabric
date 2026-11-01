package com.nukateam.ntgl.common.handlers;

import com.nukateam.ntgl.Config;
import com.nukateam.ntgl.Ntgl;
import com.nukateam.ntgl.common.data.WeaponData;
import com.nukateam.ntgl.common.data.constants.SoundTypes;
import com.nukateam.ntgl.common.data.holders.AnimationType;
import com.nukateam.ntgl.common.foundation.init.ModSounds;
import com.nukateam.ntgl.common.foundation.init.NtglGameEvents;
import com.nukateam.ntgl.common.foundation.item.interfaces.IWeapon;
import com.nukateam.ntgl.common.network.PacketHandler;
import com.nukateam.ntgl.common.network.message.weapon.S2CMessageGunSound;
import com.nukateam.ntgl.common.util.trackers.EquipTracker;
import com.nukateam.ntgl.common.event.*;
import com.nukateam.ntgl.common.util.util.FuelUtils;
import com.nukateam.ntgl.common.util.util.WeaponModifierHelper;
import com.nukateam.ntgl.common.util.util.WeaponStateHelper;
import com.nukateam.ntgl.modules.network.LevelLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
public class GunEventHandler {
    public static void register() {
        WeaponEvents.RELOAD_PRE.register((data, hand) -> {
            if (!data.wielder.level().isClientSide()) {
                PacketHandler.sendAnimation(data.wielder, hand, AnimationType.RELOAD);
            }
            return false;
        });
        WeaponEvents.FIRE_PRE.register(GunEventHandler::preShoot);
        WeaponEvents.FIRE_POST.register(GunEventHandler::postShoot);
    }

    private static boolean preShoot(LivingEntity entity, ItemStack stack, InteractionHand hand) {
        var heldItem = entity.getItemInHand(hand);
        if (heldItem.getItem() instanceof IWeapon) {
            boolean equipping = entity instanceof Player player && EquipTracker.isEquiping(player, hand);
            boolean broken = isBroken(entity, heldItem);
            boolean noFuel = !FuelUtils.hasFuel(new WeaponData(stack, entity), false);
            return equipping || broken || noFuel;
        }
        return false;
    }

    private static void postShoot(LivingEntity entity, ItemStack stack, InteractionHand hand) {
        var level = entity.level();
        var heldItem = entity.getItemInHand(hand);
        var data = new WeaponData(heldItem, entity);

        if (heldItem.getItem() instanceof IWeapon) {
            if (heldItem.isDamageableItem() && heldItem.getTag() != null) {
                if (WeaponStateHelper.hasAmmo(data)) {
                    damageGun(heldItem, level, entity);
                }
                if (heldItem.getDamageValue() >= (heldItem.getMaxDamage() / 1.5)) {
                    level.playSound(entity, entity.blockPosition(), SoundEvents.ANVIL_LAND, SoundSource.PLAYERS, 1.0F, 1.75F);
                }
            }

            if(!entity.level().isClientSide()){
                PacketHandler.sendAnimation(entity, hand, AnimationType.FIRE);

                if(!WeaponModifierHelper.isSilencedFire(data)){
                    NtglGameEvents.gunshotEvent(level, entity);
                    level.gameEvent(entity, GameEvent.PROJECTILE_SHOOT, entity.blockPosition());
                }

            }
        }
    }

    private static boolean isBroken(LivingEntity shooter, ItemStack heldItem) {
        var level = shooter.level();

        if (heldItem.isDamageableItem()) {
            int maxDamage = heldItem.getMaxDamage();
            int currentDamage = heldItem.getDamageValue();

            if (currentDamage == (maxDamage - 1)) {
                level.playSound(
                        shooter, shooter.blockPosition(),
                        SoundEvents.ITEM_BREAK, SoundSource.PLAYERS,
                        1.0F, 1.0F
                );
                return true;
            }

            if (currentDamage == maxDamage) {
                playCockSound(new WeaponData(heldItem, shooter));
                return true;
            }
        }
        return false;
    }

    public static void playCockSound(WeaponData data) {
        var wielder = data.wielder;
        if(!wielder.level().isClientSide) {
            var cockSound = WeaponModifierHelper.getSound(SoundTypes.COCK, data);
            if (!wielder.isAlive()) return;

            if (cockSound == null) cockSound = ModSounds.ITEM_PISTOL_COCK.getLocation();

            var radius = Config.SERVER.reloadMaxDistance.get();
            var messageSound = new S2CMessageGunSound(cockSound,
                    SoundSource.PLAYERS, wielder,
                    1.0F, 1.0F,
                    true);

            PacketHandler.getPlayChannel().sendToNearbyPlayers(
                    () -> LevelLocation.create(wielder.level(), wielder.getX(), wielder.getY() + 1.0, wielder.getZ(), radius),
                    messageSound);
        }
    }


    public static void damageGun(ItemStack stack, Level level, LivingEntity entity) {
        if (entity instanceof Player player && player.getAbilities().instabuild)
            return;

        if (stack.isDamageableItem()) {
            int maxDamage = stack.getMaxDamage();
            int currentDamage = stack.getDamageValue();
            if (currentDamage >= (maxDamage - 1)) {
                if (currentDamage >= (maxDamage - 2)) {
                    level.playSound(entity, entity.blockPosition(), SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
                }
            } else {
                stack.hurtAndBreak(1, entity, null);
            }
        }
    }
}