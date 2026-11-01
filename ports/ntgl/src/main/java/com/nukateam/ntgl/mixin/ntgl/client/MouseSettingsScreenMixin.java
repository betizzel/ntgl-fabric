package com.nukateam.ntgl.mixin.ntgl.client;

import com.nukateam.ntgl.client.settings.OptionInstances;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.MouseSettingsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseSettingsScreen.class)
public abstract class MouseSettingsScreenMixin {
    @Shadow private OptionsList list;

    @Inject(method = "init", at = @At("TAIL"))
    private void ntgl$addAimSensitivity(CallbackInfo ci) {
        list.addSmall(OptionInstances.createSensitivitySlider(), null);
    }
}
