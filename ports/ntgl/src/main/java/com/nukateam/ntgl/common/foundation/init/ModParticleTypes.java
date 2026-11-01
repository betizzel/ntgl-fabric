package com.nukateam.ntgl.common.foundation.init;

import com.nukateam.ntgl.common.foundation.particles.*;
import com.nukateam.ntgl.Ntgl;
import com.mojang.serialization.Codec;
import net.minecraft.core.particles.*;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

/**
 * Author: MrCrayfish
 */
public class ModParticleTypes {

    public static final ParticleType<BulletHoleData> BULLET_HOLE = Registry.register(BuiltInRegistries.PARTICLE_TYPE, new ResourceLocation(Ntgl.MOD_ID, "bullet_hole"), new ParticleType<>(false, BulletHoleData.DESERIALIZER) {
        @Override
        public Codec<BulletHoleData> codec() {
            return BulletHoleData.CODEC;
        }
    });
    public static final SimpleParticleType BLOOD = Registry.register(BuiltInRegistries.PARTICLE_TYPE, new ResourceLocation(Ntgl.MOD_ID, "blood"), FabricParticleTypes.simple(true));
    public static final ParticleType<TrailData> TRAIL = Registry.register(BuiltInRegistries.PARTICLE_TYPE, new ResourceLocation(Ntgl.MOD_ID, "trail"), new ParticleType<>(false, TrailData.DESERIALIZER) {
        @Override
        public Codec<TrailData> codec() {
            return TrailData.CODEC;
        }
    });

    public static void init() {
        // Triggers class initialization and registers particle types.
    }
}
