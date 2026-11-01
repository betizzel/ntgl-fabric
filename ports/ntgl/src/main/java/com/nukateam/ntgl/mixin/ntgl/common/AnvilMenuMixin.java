package com.nukateam.ntgl.mixin.ntgl.common;

import com.nukateam.ntgl.common.foundation.item.WeaponItem;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin extends ItemCombinerMenu {
    @Shadow @Final private DataSlot cost;

    protected AnvilMenuMixin(MenuType<?> menuType, int containerId, Inventory inventory, ContainerLevelAccess access) {
        super(menuType, containerId, inventory, access);
    }

    @Inject(method = "createResult", at = @At("HEAD"), cancellable = true)
    private void ntgl$checkBookEnchantability(CallbackInfo ci) {
        ItemStack input = inputSlots.getItem(0);
        ItemStack book = inputSlots.getItem(1);
        if (book.is(Items.ENCHANTED_BOOK) && input.getItem() instanceof WeaponItem weapon && !weapon.isBookEnchantable(input, book)) {
            resultSlots.setItem(0, ItemStack.EMPTY);
            cost.set(0);
            broadcastChanges();
            ci.cancel();
        }
    }
}
