package com.nukateam.ntgl.common.util.helpers.compatibility.backpack;

import com.nukateam.ntgl.common.data.holders.AmmoHolder;
import com.nukateam.ntgl.common.util.helpers.context.AmmoContext;
import com.nukateam.ntgl.common.util.helpers.context.IAmmoContext;
import com.nukateam.ntgl.common.util.util.InventoryUtil;
import com.yyz.yyzsbackpack.util.BackpackHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public final class YyzBackpackHelper {
    private YyzBackpackHelper() {}

    public static IAmmoContext findAmmo(Player player, AmmoHolder holder) {
        return find(player, holder, false);
    }

    public static IAmmoContext findMagazine(Player player, AmmoHolder holder) {
        return find(player, holder, true);
    }

    private static IAmmoContext find(Player player, AmmoHolder holder, boolean magazine) {
        Inventory inventory = player.getInventory();
        int best = -1;
        for (int i = 36; i < BackpackHelper.getBackpackSize(player); i++) {
            ItemStack stack = inventory.getItem(i);
            if (!InventoryUtil.isAmmo(stack, holder)) continue;
            if (!magazine || stack.getDamageValue() == 0) return new Context(stack, inventory, i);
            if (best < 0 || (stack.getDamageValue() < inventory.getItem(best).getDamageValue()
                    && inventory.getItem(best).getDamageValue() < inventory.getItem(best).getMaxDamage())) best = i;
        }
        return best < 0 ? AmmoContext.NONE : new Context(inventory.getItem(best), inventory, best);
    }

    private record Context(ItemStack stack, Inventory inventory, int slot) implements IAmmoContext {
        @Override
        public void shrink(int amount, AmmoHolder holder, LivingEntity entity) {
            inventory.removeItem(slot, amount);
            inventory.setChanged();
        }
    }
}
