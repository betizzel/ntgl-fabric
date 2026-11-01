package com.nukateam.ntgl.common.foundation.init;

import com.nukateam.ntgl.Ntgl;
import com.nukateam.ntgl.common.foundation.entity.FlyingGib;
import com.nukateam.ntgl.common.foundation.entity.misc.AshPile;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntityTypes {
    public static final EntityType<AshPile> ASH_PILE = registerEntity("ash_pile", EntityType.Builder
            .<AshPile>of(AshPile::new, MobCategory.MISC).sized(0.8f, 0.2f));
    public static final EntityType<FlyingGib> FLYING_GIBS = registerEntity("flying_gibs", EntityType.Builder
            .<FlyingGib>of(FlyingGib::new, MobCategory.MISC).sized(0.5f, 0.5f));

    private static <T extends Entity> EntityType<T> registerEntity(String name, EntityType.Builder<T> builder) {
        ResourceLocation id = new ResourceLocation(Ntgl.MOD_ID, name);
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, id, builder.build(id.toString()));
    }

    public static void init() {}
}
