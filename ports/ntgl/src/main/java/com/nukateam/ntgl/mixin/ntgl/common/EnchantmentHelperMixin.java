package com.nukateam.ntgl.mixin.ntgl.common;

import com.nukateam.ntgl.common.foundation.item.attachment.AttachmentItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {
    @Inject(method = "getAvailableEnchantmentResults", at = @At("HEAD"), cancellable = true)
    private static void ntgl$attachmentEnchantments(int power, ItemStack stack, boolean treasureAllowed,
                                                    CallbackInfoReturnable<List<EnchantmentInstance>> cir) {
        if (!(stack.getItem() instanceof AttachmentItem<?> attachment)) return;
        List<EnchantmentInstance> results = new ArrayList<>();
        for (var enchantment : BuiltInRegistries.ENCHANTMENT) {
            if ((!treasureAllowed && enchantment.isTreasureOnly()) || !enchantment.isDiscoverable()
                    || !attachment.canApplyAtEnchantingTable(stack, enchantment)) continue;
            for (int level = enchantment.getMaxLevel(); level >= enchantment.getMinLevel(); level--) {
                if (power >= enchantment.getMinCost(level) && power <= enchantment.getMaxCost(level)) {
                    results.add(new EnchantmentInstance(enchantment, level));
                    break;
                }
            }
        }
        cir.setReturnValue(results);
    }
}
