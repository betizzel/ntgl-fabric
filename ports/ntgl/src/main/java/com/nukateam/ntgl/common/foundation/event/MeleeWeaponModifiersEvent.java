package com.nukateam.ntgl.common.foundation.event;

import com.nukateam.ntgl.common.data.WeaponData;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public abstract class MeleeWeaponModifiersEvent {
    @FunctionalInterface
    public interface DamageCallback {
        void modify(Damage event);
    }

    @FunctionalInterface
    public interface CooldownCallback {
        void modify(Cooldown event);
    }

    public static final Event<DamageCallback> DAMAGE = EventFactory.createArrayBacked(DamageCallback.class, listeners -> event -> {
        for (var listener : listeners) listener.modify(event);
    });

    public static final Event<CooldownCallback> COOLDOWN = EventFactory.createArrayBacked(CooldownCallback.class, listeners -> event -> {
        for (var listener : listeners) listener.modify(event);
    });

    private final WeaponData weaponData;
    
    public MeleeWeaponModifiersEvent(WeaponData weaponData) {
        this.weaponData = weaponData;
    }

    public WeaponData getWeaponData() {
        return weaponData;
    }

    public static class Damage extends MeleeWeaponModifiersEvent {
        private float modifier;

        public Damage(WeaponData weaponData, float modifier) {
            super(weaponData);
            this.modifier = modifier;
        }

        public float getModifier() {
            return modifier;
        }

        public void setModifier(float modifier) {
            this.modifier = modifier;
        }
    }

    public static class Cooldown extends MeleeWeaponModifiersEvent {
        private int modifier;

        public Cooldown(WeaponData weaponData, int modifier) {
            super(weaponData);
            this.modifier = modifier;
        }

        public int getModifier() {
            return modifier;
        }

        public void setModifier(int modifier) {
            this.modifier = modifier;
        }
    }
}
