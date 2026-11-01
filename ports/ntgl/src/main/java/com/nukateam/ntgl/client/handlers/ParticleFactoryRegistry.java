package com.nukateam.ntgl.client.handlers;

import com.nukateam.ntgl.Ntgl;
import com.nukateam.ntgl.client.render.particle.BloodParticle;
import com.nukateam.ntgl.client.render.particle.BulletHoleParticle;
import com.nukateam.ntgl.client.render.particle.TrailParticle;
import com.nukateam.ntgl.common.foundation.init.ModParticleTypes;

public class ParticleFactoryRegistry {
    public static void register() {
        net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry.getInstance().register(ModParticleTypes.BULLET_HOLE, (typeIn, worldIn, x, y, z, xSpeed, ySpeed, zSpeed) -> new BulletHoleParticle(worldIn, x, y, z, typeIn.getDirection(), typeIn.getPos()));
        net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry.getInstance().register(ModParticleTypes.BLOOD, BloodParticle.Factory::new);
        net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry.getInstance().register(ModParticleTypes.TRAIL, TrailParticle.Factory::new);
    }
}
