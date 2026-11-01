package com.nukateam.ntgl.client.util.handler;

import com.mojang.blaze3d.vertex.PoseStack;
import com.nukateam.ntgl.common.data.WeaponData;
import com.nukateam.ntgl.common.foundation.item.interfaces.IWeapon;
import com.nukateam.ntgl.common.util.util.WeaponModifierHelper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;

public class EntityModelHandler {
    public static void onRenderEntityPre(LivingEntity entity, float partialTick, PoseStack matrices, MultiBufferSource buffers) {
        var heldItem = entity.getMainHandItem();
        if (heldItem.getItem() instanceof IWeapon) {
            var animation = WeaponModifierHelper.getGripType(new WeaponData(heldItem, entity)).getHeldAnimation();
            animation.applyEntityPreRender(entity, InteractionHand.MAIN_HAND,
                    AimingHandler.get().getAimProgress(entity, partialTick), matrices, buffers);
        }
    }
}
