package com.nukateam.ntgl.common.util.helpers.compatibility;

import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import javax.annotation.Nullable;
import java.util.function.Predicate;

public final class CuriosHelper {
    private CuriosHelper() {}

    @Nullable
    public static ItemStack getItem(Player player, Predicate<ItemStack> filter) {
        return TrinketsApi.getTrinketComponent(player)
                .flatMap(component -> component.getEquipped(filter).stream().findFirst())
                .map(pair -> pair.getB())
                .orElse(null);
    }
}
