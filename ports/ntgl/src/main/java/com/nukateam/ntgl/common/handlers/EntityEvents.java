package com.nukateam.ntgl.common.handlers;

import com.google.common.collect.HashMultimap;
import com.nukateam.ntgl.Ntgl;
import com.nukateam.ntgl.common.data.WeaponData;
import com.nukateam.ntgl.common.foundation.entity.projectile.DeathEffect;
import com.nukateam.ntgl.common.foundation.item.interfaces.IWeapon;
import com.nukateam.ntgl.common.util.util.WeaponModifierHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.core.registries.BuiltInRegistries;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EntityEvents {
    public static Map<UUID, HashMultimap<Attribute, AttributeModifier>> PLAYER_MODIFIERS = new HashMap<>();

    public static void register() {
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> DeathEffect.createDeathEffect(entity, source));
    }

    public static void onLivingTick(LivingEntity entity) {
        var mods = PLAYER_MODIFIERS.get(entity.getUUID());
        if (mods != null) {
            entity.getAttributes().removeAttributeModifiers(mods);
        }
        applyAttributeModifiers(entity, InteractionHand.MAIN_HAND);
        applyAttributeModifiers(entity, InteractionHand.OFF_HAND);
    }

    private static void applyAttributeModifiers(LivingEntity player, InteractionHand hand) {
        var heldItem = player.getItemInHand(hand);

        if (heldItem.getItem() instanceof IWeapon) {
            var modifiers = WeaponModifierHelper.getAttributeModifiers(new WeaponData(heldItem, player));
            var multiMap = HashMultimap.<Attribute, AttributeModifier>create();

            for (var modifier : modifiers) {
                var attribute = BuiltInRegistries.ATTRIBUTE.getOptional(modifier.getAttribute()).orElse(null); if (attribute == null) continue;
                var attributeInstance = player.getAttribute(attribute); if (attributeInstance == null) continue;
                var name = modifier.getAttribute().toString() + hand;
                var uuid = UUID.nameUUIDFromBytes(name.getBytes(StandardCharsets.UTF_8));
                var newModifier = new AttributeModifier(uuid, name, modifier.getValue(), modifier.getOperation());

                if (!attributeInstance.hasModifier(newModifier)) {
                    attributeInstance.addTransientModifier(newModifier);
                }
                multiMap.put(attribute, newModifier);
            }
            PLAYER_MODIFIERS.put(player.getUUID(), multiMap);
        }
    }
}
