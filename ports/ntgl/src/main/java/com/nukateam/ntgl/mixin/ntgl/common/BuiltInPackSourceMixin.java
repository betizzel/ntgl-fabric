package com.nukateam.ntgl.mixin.ntgl.common;

import com.nukateam.ntgl.modules.gunpack.resource.NTGLPackManager;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.BuiltInPackSource;
import net.minecraft.server.packs.repository.Pack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(BuiltInPackSource.class)
public abstract class BuiltInPackSourceMixin {
    @Shadow @Final private PackType packType;

    @Inject(method = "loadPacks", at = @At("TAIL"))
    private void ntgl$discoverGunPacks(Consumer<Pack> consumer, CallbackInfo ci) {
        NTGLPackManager.scanPacks();
        NTGLPackManager.addPacks(packType, consumer);
    }
}
