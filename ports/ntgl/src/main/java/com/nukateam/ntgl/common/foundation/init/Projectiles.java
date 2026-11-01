package com.nukateam.ntgl.common.foundation.init;

import com.nukateam.ntgl.Ntgl;
import com.nukateam.ntgl.common.foundation.entity.*;
import com.nukateam.ntgl.common.foundation.entity.throwable.ThrowableGrenadeEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import java.util.function.BiFunction;

public class Projectiles {
    public static final EntityType<ProjectileEntity> PROJECTILE = registerProjectile("projectile", ProjectileEntity::new);
    public static final EntityType<LaserProjectile> LASER_PROJECTILE = registerBasic("laser_projectile", LaserProjectile::new);
    public static final EntityType<ContinuousLaserProjectile> CONTINUOUS_LASER_PROJECTILE = registerBasic("continuous_laser_projectile", ContinuousLaserProjectile::new);
    public static final EntityType<TeslaProjectile> TESLA_PROJECTILE = registerBasic("tesla_projectile", TeslaProjectile::new);
    public static final EntityType<FlameProjectile> FLAME_PROJECTILE = registerBasic("flame_projectile", FlameProjectile::new);
    public static final EntityType<GrenadeEntity> GRENADE = registerBasic("grenade", GrenadeEntity::new);
    public static final EntityType<MissileEntity> MISSILE = registerBasic("missile", MissileEntity::new);
    public static final EntityType<ThrowableGrenadeEntity> THROWABLE_GRENADE = registerBasic("throwable_grenade", ThrowableGrenadeEntity::new);
    public static final EntityType<StunGrenadeEntity> THROWABLE_STUN_GRENADE = registerBasic("throwable_stun_grenade", StunGrenadeEntity::new);
    public static final EntityType<ProjectileEntity> ARROW_LIKE = registerBasic("arrow_like", ArrowLikeProjectile::new);

    // Ordinary bullets are simulated on the server and must not be tracked by clients.
    private static <T extends ProjectileEntity> EntityType<T> registerProjectile(String id, BiFunction<EntityType<T>, Level, T> factory) {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(Ntgl.MOD_ID, id),
                FabricEntityTypeBuilder.<T>create(MobCategory.MISC, factory::apply)
                        .dimensions(EntityDimensions.fixed(0.25F, 0.25F))
                        .trackRangeChunks(0).disableSummon().fireImmune()
                        .forceTrackedVelocityUpdates(false).build());
    }

    private static <T extends Entity> EntityType<T> registerBasic(String id, BiFunction<EntityType<T>, Level, T> factory) {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(Ntgl.MOD_ID, id),
                FabricEntityTypeBuilder.<T>create(MobCategory.MISC, factory::apply)
                        .dimensions(EntityDimensions.fixed(0.25F, 0.25F))
                        .trackRangeChunks(100).trackedUpdateRate(1).disableSummon().fireImmune()
                        .forceTrackedVelocityUpdates(true).build());
    }

    public static void init() {}
}
