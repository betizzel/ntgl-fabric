package com.nukateam.ntgl.mixin.ntgl.client;

import net.minecraft.client.renderer.ItemInHandRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemInHandRenderer.class)
public interface ItemInHandRendererAccessor {
    @Accessor("mainHandHeight") float ntgl$getMainHandHeight();
    @Accessor("oMainHandHeight") float ntgl$getOldMainHandHeight();
}
