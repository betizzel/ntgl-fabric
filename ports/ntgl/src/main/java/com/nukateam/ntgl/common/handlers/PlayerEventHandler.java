package com.nukateam.ntgl.common.handlers;

import com.mojang.datafixers.util.Pair;
import com.nukateam.ntgl.Ntgl;
import com.nukateam.ntgl.client.util.handler.ClientEquipHandler;
import com.nukateam.ntgl.common.data.WeaponData;
import com.nukateam.ntgl.common.util.trackers.EquipTracker;
import com.nukateam.ntgl.common.foundation.item.interfaces.IThrowable;
import com.nukateam.ntgl.common.util.util.WeaponModifierHelper;
import net.minecraft.nbt.Tag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class PlayerEventHandler {
    private static final Map<Pair<InteractionHand, Player>, Slot> lastSelectedSlots = new HashMap<>();
    public static final String ID = "WeaponId";

    public static void onPlayerTick(Player player) {
        tryEquip(player, InteractionHand.MAIN_HAND);
        tryEquip(player, InteractionHand.OFF_HAND);
    }

    public static void onChangeEquipment(LivingEntity entity, EquipmentSlot slot, ItemStack oldItem, ItemStack newItem) {
        if(slot == EquipmentSlot.MAINHAND || slot == EquipmentSlot.OFFHAND) {
            var hand = getHand(slot);
            if (newItem.getItem() instanceof IThrowable throwable) {
                if (newItem.getCount() < oldItem.getCount()) {

                    var data = new WeaponData(newItem, entity);
                    var equipTime = WeaponModifierHelper.getEquipTime(data);
                    EquipTracker.stopEquip(entity, hand);
                    EquipTracker.startEquip(entity, hand, equipTime);
                }
            }
            else EquipTracker.stopEquip(entity, hand);
        }
    }

    private static void tryEquip(Player player, InteractionHand hand) {
        var key = new Pair<>(hand, player);
        var lastSlot = lastSelectedSlots.getOrDefault(key, new Slot(ItemStack.EMPTY, 0, 0));
        var newItem = player.getItemInHand(hand);

        if (newItem.getItem() != lastSlot.stack.getItem() || newItem.getCount() < lastSlot.stackSize()
                || player.getInventory().selected != lastSlot.slotId) {

            if (newItem.getItem() instanceof IThrowable) {
                if (newItem.getCount() < lastSlot.stackSize()) {
                    var data = new WeaponData(newItem, player);
                    var equipTime = WeaponModifierHelper.getEquipTime(data);
                    ClientEquipHandler.get().setEquiping(hand, equipTime);
                }
            }

            lastSelectedSlots.put(key, new Slot(newItem, newItem.getCount(), player.getInventory().selected));
        }
    }

    private static @NotNull InteractionHand getHand(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
    }

    private static String getId(ItemStack stack) {
        var lastItemTag = stack.getOrCreateTag();
        return lastItemTag.contains(ID, Tag.TAG_STRING) ?
                lastItemTag.getString(ID):
                UUID.randomUUID().toString();
    }

    record Slot(ItemStack stack, int stackSize, int slotId){}
}