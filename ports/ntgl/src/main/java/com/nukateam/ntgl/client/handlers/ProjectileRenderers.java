package com.nukateam.ntgl.client.handlers;

import com.nukateam.ntgl.client.render.renderers.misc.AshPileRenderer;
import com.nukateam.ntgl.client.render.renderers.misc.FlyingGibsRenderer;
import com.nukateam.ntgl.client.render.renderers.projectiles.*;
import com.nukateam.ntgl.common.foundation.init.ModEntityTypes;
import com.nukateam.ntgl.common.foundation.init.Projectiles;
import com.nukateam.ntgl.Ntgl;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;


public class ProjectileRenderers {
    public static void registerEntityRenders() {
        EntityRendererRegistry.register(Projectiles.PROJECTILE, ProjectileRenderer::new);
        EntityRendererRegistry.register(Projectiles.ARROW_LIKE, ProjectileRenderer::new);
        EntityRendererRegistry.register(Projectiles.LASER_PROJECTILE, LaserProjectileRenderer::new);
        EntityRendererRegistry.register(Projectiles.CONTINUOUS_LASER_PROJECTILE, LaserProjectileRenderer::new);
        EntityRendererRegistry.register(Projectiles.TESLA_PROJECTILE, TeslaProjectileRenderer::new);
        EntityRendererRegistry.register(Projectiles.FLAME_PROJECTILE, FlameRenderer::new);
        EntityRendererRegistry.register(Projectiles.GRENADE, GrenadeRenderer::new);
        EntityRendererRegistry.register(Projectiles.MISSILE, MissileRenderer::new);
        EntityRendererRegistry.register(Projectiles.THROWABLE_GRENADE, ThrowableItemRenderer::new);
        EntityRendererRegistry.register(Projectiles.THROWABLE_STUN_GRENADE, ThrowableItemRenderer::new);

        EntityRendererRegistry.register(ModEntityTypes.FLYING_GIBS, FlyingGibsRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.ASH_PILE, AshPileRenderer::new);
    }
}
