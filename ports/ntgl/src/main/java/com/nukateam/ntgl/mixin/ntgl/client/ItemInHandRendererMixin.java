package com.nukateam.ntgl.mixin.ntgl.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.nukateam.ntgl.client.util.handler.ClientReloadHandler;
import com.nukateam.ntgl.client.util.handler.RecoilHandler;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {
    @Shadow private ItemStack mainHandItem;
    @Shadow private ItemStack offHandItem;
    @Unique private int ntgl$selectedSlot = -1;

    @Inject(method = "tick", at = @At("HEAD"))
    private void ntgl$preserveWeaponEquipProgress(CallbackInfo ci) {
        var player = net.minecraft.client.Minecraft.getInstance().player;
        if (player == null) return;
        int slot = player.getInventory().selected;
        ItemStack current = player.getMainHandItem();
        if (mainHandItem.getItem() instanceof com.nukateam.ntgl.common.foundation.item.WeaponItem weapon &&
                !weapon.shouldCauseReequipAnimation(mainHandItem, current, ntgl$selectedSlot != slot)) {
            mainHandItem = current;
        }
        current = player.getOffhandItem();
        if (offHandItem.getItem() instanceof com.nukateam.ntgl.common.foundation.item.WeaponItem weapon &&
                !weapon.shouldCauseReequipAnimation(offHandItem, current, false)) {
            offHandItem = current;
        }
        ntgl$selectedSlot = slot;
    }

    @Inject(method = "renderArmWithItem", at = @At("HEAD"), cancellable = true)
    private void ntgl$hand(AbstractClientPlayer player, float tickDelta, float pitch, InteractionHand hand,
                           float swing, ItemStack stack, float equip, PoseStack matrices,
                           MultiBufferSource buffers, int light, CallbackInfo ci) {
        RecoilHandler.get().onRenderOverlay(hand, stack);
        var reload = ClientReloadHandler.get();
        if ((hand == InteractionHand.MAIN_HAND && reload.isReloadingLeft(player)) ||
                (hand == InteractionHand.OFF_HAND && reload.isReloadingRight(player))) {
            ci.cancel();
            return;
        }
        if (com.nukateam.ntgl.client.util.handler.WeaponRenderingHandler.get().onRenderOverlay(hand, stack, matrices, buffers, tickDelta)) ci.cancel();
    }
}
