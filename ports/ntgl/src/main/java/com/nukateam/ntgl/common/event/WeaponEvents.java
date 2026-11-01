package com.nukateam.ntgl.common.event;

import com.nukateam.ntgl.common.data.WeaponData;
import com.nukateam.ntgl.common.foundation.entity.ProjectileEntity;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.HitResult;
import java.util.ArrayList;

/** Weapon lifecycle callbacks. Pre and hit callbacks return true to cancel the action. */
public final class WeaponEvents {
    private WeaponEvents() {}

    @FunctionalInterface
    public interface FirePre { boolean onFire(LivingEntity entity, ItemStack stack, InteractionHand hand); }
    @FunctionalInterface
    public interface FirePost { void onFire(LivingEntity entity, ItemStack stack, InteractionHand hand); }
    @FunctionalInterface
    public interface ReloadPre { boolean onReload(WeaponData data, InteractionHand hand); }
    @FunctionalInterface
    public interface ReloadPost { void onReload(WeaponData data, InteractionHand hand); }
    @FunctionalInterface
    public interface ProjectileHit { boolean onHit(HitResult result, ProjectileEntity projectile); }
    @FunctionalInterface
    public interface MeleePre { boolean onAttack(LivingEntity entity, WeaponData data, InteractionHand hand, ArrayList<LivingEntity> targets); }
    @FunctionalInterface
    public interface MeleePost { void onAttack(LivingEntity entity, WeaponData data, InteractionHand hand, ArrayList<LivingEntity> targets); }

    public static final Event<FirePre> FIRE_PRE = EventFactory.createArrayBacked(FirePre.class, listeners -> (entity, stack, hand) -> {
        for (var listener : listeners) if (listener.onFire(entity, stack, hand)) return true;
        return false;
    });
    public static final Event<FirePost> FIRE_POST = EventFactory.createArrayBacked(FirePost.class, listeners -> (entity, stack, hand) -> {
        for (var listener : listeners) listener.onFire(entity, stack, hand);
    });
    public static final Event<ReloadPre> RELOAD_PRE = EventFactory.createArrayBacked(ReloadPre.class, listeners -> (data, hand) -> {
        for (var listener : listeners) if (listener.onReload(data, hand)) return true;
        return false;
    });
    public static final Event<ReloadPost> RELOAD_POST = EventFactory.createArrayBacked(ReloadPost.class, listeners -> (data, hand) -> {
        for (var listener : listeners) listener.onReload(data, hand);
    });
    public static final Event<ProjectileHit> PROJECTILE_HIT = EventFactory.createArrayBacked(ProjectileHit.class, listeners -> (result, projectile) -> {
        for (var listener : listeners) if (listener.onHit(result, projectile)) return true;
        return false;
    });
    public static final Event<MeleePre> MELEE_PRE = EventFactory.createArrayBacked(MeleePre.class, listeners -> (entity, data, hand, targets) -> {
        for (var listener : listeners) if (listener.onAttack(entity, data, hand, targets)) return true;
        return false;
    });
    public static final Event<MeleePost> MELEE_POST = EventFactory.createArrayBacked(MeleePost.class, listeners -> (entity, data, hand, targets) -> {
        for (var listener : listeners) listener.onAttack(entity, data, hand, targets);
    });
}
