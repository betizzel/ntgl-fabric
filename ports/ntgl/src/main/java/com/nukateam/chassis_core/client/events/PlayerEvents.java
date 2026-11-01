package com.nukateam.chassis_core.client.events;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.HumanoidArm;

import static com.nukateam.chassis_core.common.util.helpers.PlayerUtils.*;

public class PlayerEvents {
    public static boolean renderHand(HumanoidArm arm, PoseStack poseStack, MultiBufferSource buffers, int light) {
        if (!isLocalWearingChassis() || !getLocalPlayerChassis().renderHand()) return false;
        poseStack.pushPose();
        boolean right = arm == HumanoidArm.RIGHT;
        int side = right ? 1 : -1;
        poseStack.mulPose(Axis.XP.rotationDegrees(90));
        if (right) {
            poseStack.translate(18 / 10D / 16D, 91 / 10D / 16D, -155 / 10D / 16D);
        } else {
            poseStack.translate(140 / 10D / 16D, 106 / 10D / 16D, -155 / 10D / 16D);
        }
        poseStack.mulPose(Axis.ZP.rotationDegrees(180 * side));
        renderChassisHand(arm, poseStack, buffers, light);
        poseStack.popPose();
        return true;
    }
}
