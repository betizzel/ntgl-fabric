package com.nukateam.ntgl.common.util.helpers.compatibility.backpack;

import com.nukateam.ntgl.Ntgl;
import com.nukateam.ntgl.common.data.holders.AmmoHolder;
import com.nukateam.ntgl.common.util.helpers.compatibility.CuriosHelper;
import com.nukateam.ntgl.common.util.helpers.context.AmmoContext;
import com.nukateam.ntgl.common.util.helpers.context.IAmmoContext;
import com.nukateam.ntgl.common.util.util.InventoryUtil;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedbackpacks.common.BackpackWrapperLookup;

public final class SophisticatedHelper {
    private SophisticatedHelper() {}

    public static IAmmoContext findAmmo(Player player, AmmoHolder holder) {
        return find(player, holder, false);
    }

    public static IAmmoContext findMagazine(Player player, AmmoHolder holder) {
        return find(player, holder, true);
    }

    private static IAmmoContext find(Player player, AmmoHolder holder, boolean magazine) {
        ItemStack backpack = null;
        if (Ntgl.curiosLoaded) backpack = CuriosHelper.getItem(player, stack -> stack.getItem() instanceof BackpackItem);
        if (backpack == null) {
            ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
            if (chest.getItem() instanceof BackpackItem) backpack = chest;
        }
        if (backpack == null) return AmmoContext.NONE;
        var wrapper = BackpackWrapperLookup.get(backpack).orElse(null);
        if (wrapper == null) return AmmoContext.NONE;
        var inventory = wrapper.getInventoryHandler();
        int best = -1;
        for (int i = 0; i < inventory.getSlotCount(); i++) {
            ItemStack stack = inventory.getSlotStack(i);
            if (!InventoryUtil.isAmmo(stack, holder)) continue;
            if (!magazine || stack.getDamageValue() == 0) return new Context(stack, inventory.getSlot(i));
            if (best < 0 || (stack.getDamageValue() < inventory.getSlotStack(best).getDamageValue()
                    && inventory.getSlotStack(best).getDamageValue() < inventory.getSlotStack(best).getMaxDamage())) best = i;
        }
        return best < 0 ? AmmoContext.NONE : new Context(inventory.getSlotStack(best), inventory.getSlot(best));
    }

    private record Context(ItemStack stack, SingleSlotStorage<ItemVariant> slot) implements IAmmoContext {
        @Override
        public void shrink(int amount, AmmoHolder holder, LivingEntity entity) {
            int count = (int) Math.ceil((double) amount / holder.getValue(stack));
            ItemStack consumed = stack.copy();
            try (Transaction transaction = Transaction.openOuter()) {
                long extracted = slot.extract(ItemVariant.of(stack), count, transaction);
                if (extracted != count) return;
                transaction.commit();
            }
            for (ItemStack item : holder.onConsume().apply(consumed, amount)) entity.spawnAtLocation(item);
        }
    }
}
