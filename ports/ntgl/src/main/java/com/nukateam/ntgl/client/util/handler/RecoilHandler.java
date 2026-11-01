package com.nukateam.ntgl.client.util.handler;

import com.nukateam.ntgl.Config;
import com.nukateam.ntgl.common.data.WeaponData;
import com.nukateam.ntgl.common.data.config.weapon.WeaponConfig;

import com.nukateam.ntgl.common.foundation.item.interfaces.IWeapon;
import com.nukateam.ntgl.common.util.util.WeaponModifierHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Author: MrCrayfish
 */
public class RecoilHandler {
    private static RecoilHandler instance;
    public float lastRandPitch = 0f;
    public float lastRandYaw = 0f;

    public static RecoilHandler get() {
        if (instance == null) {
            instance = new RecoilHandler();
        }
        return instance;
    }

    private Random random = new Random();
    private double gunRecoilNormal;
    private double gunRecoilAngle;
    private float gunRecoilRandom;
    private float cameraRecoil;
    private float progressCameraRecoil;
    private final Map<InteractionHand, WeaponData> weaponData = new HashMap<>();

    private RecoilHandler() {
    }

    public void onGunFire(LivingEntity entity, ItemStack stack, InteractionHand hand) {
        if (!entity.level().isClientSide) return;

        if (!Config.SERVER.enableCameraRecoil.get())
            return;

        var data = new WeaponData(stack, entity);
        weaponData.put(hand, data);
        var recoilModifier = 1.0F - WeaponModifierHelper.getRecoilModifier(data);

        recoilModifier *= (float) this.getAdsRecoilReduction(data);
        var recoilAngle = WeaponModifierHelper.getRecoilAngle(data);
        this.cameraRecoil = recoilAngle * recoilModifier;
        this.progressCameraRecoil = 0F;
        this.gunRecoilRandom = random.nextFloat();
        this.lastRandPitch = random.nextFloat();
        this.lastRandYaw = random.nextFloat();
    }

    public void onRenderTick() {
        if (this.cameraRecoil <= 0)
            return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null)
            return;

        if (!Config.SERVER.enableCameraRecoil.get())
            return;

        float recoilAmount = this.cameraRecoil * mc.getDeltaFrameTime() * 0.15F;
        float startProgress = this.progressCameraRecoil / this.cameraRecoil;
        float endProgress = (this.progressCameraRecoil + recoilAmount) / this.cameraRecoil;

        float pitch = mc.player.getXRot();
        if (startProgress < 0.2F) {
            mc.player.setXRot(pitch - ((endProgress - startProgress) / 0.2F) * this.cameraRecoil);
        } else {
            mc.player.setXRot(pitch + ((endProgress - startProgress) / 0.8F) * this.cameraRecoil);
        }

        this.progressCameraRecoil += recoilAmount;

        if (this.progressCameraRecoil >= this.cameraRecoil) {
            this.cameraRecoil = 0;
            this.progressCameraRecoil = 0;
        }
    }

    public void onRenderOverlay(InteractionHand hand, ItemStack heldItem) {
        if (hand != InteractionHand.MAIN_HAND)
            return;
        if (!(heldItem.getItem() instanceof IWeapon weaponItem))
            return;

        var modifiedGun = weaponItem.getModifiedConfig(heldItem);
        var cooldown = ClientShootingHandler.get().getCooldownPercent(Minecraft.getInstance().player, hand);
        var recoilDurationOffset = modifiedGun.getGeneral().getRecoilDurationOffset();

        cooldown = cooldown >= recoilDurationOffset ?
                (cooldown - recoilDurationOffset) / (1.0F - recoilDurationOffset) : 0.0F;
        if (cooldown >= 0.8) {
            float amount = (1.0F - cooldown) / 0.2F;
            this.gunRecoilNormal = 1 - (--amount) * amount * amount * amount;
        } else {
            float amount = (cooldown / 0.8F);
            this.gunRecoilNormal = amount < 0.5 ? 2 * amount * amount : -1 + (4 - 2 * amount) * amount;
        }

        var data = weaponData.getOrDefault(hand, new WeaponData(heldItem, Minecraft.getInstance().player));
        this.gunRecoilAngle = WeaponModifierHelper.getRecoilAngle(data);
    }

    public double getAdsRecoilReduction(WeaponData data) {
        var recoilAdsReduction = WeaponModifierHelper.getRecoilAdsReduction(data);
        var normalAdsProgress = AimingHandler.get().getNormalisedAdsProgress();
        return 1.0 - recoilAdsReduction * normalAdsProgress;
    }

    public double getGunRecoilNormal() {
        return this.gunRecoilNormal;
    }

    public double getGunRecoilAngle() {
        return this.gunRecoilAngle;
    }

    public float getGunRecoilRandom() {
        return this.gunRecoilRandom;
    }
}
