package com.nukateam.example.common.registery;

import com.nukateam.example.common.entities.Brahmin;
import com.nukateam.example.common.entities.Raider;
import com.nukateam.ntgl.Ntgl;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class EntityTypes {
    public static EntityType<Raider> RAIDER;
    public static EntityType<Brahmin> BRAHMIN;

    public static void register() {
        RAIDER = Registry.register(BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(Ntgl.MOD_ID, "raider"),
                EntityType.Builder.of(Raider::new, MobCategory.CREATURE).sized(1.0f, 1.9f).build(Ntgl.MOD_ID + ":raider"));
        BRAHMIN = Registry.register(BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(Ntgl.MOD_ID, "brahmin"),
                EntityType.Builder.of(Brahmin::new, MobCategory.CREATURE).sized(1.5f, 1.5f).build(Ntgl.MOD_ID + ":brahmin"));
        FabricDefaultAttributeRegistry.register(RAIDER, Raider.createAttributes());
        FabricDefaultAttributeRegistry.register(BRAHMIN, Brahmin.createAttributes());
    }
}
