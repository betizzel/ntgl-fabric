package com.nukateam.chassis_core.modules.example.common.registery;

import com.nukateam.chassis_core.ChassisCore;
import com.nukateam.chassis_core.modules.example.common.entities.ExampleChassis;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class EntityTypes {
    public static final EntityType<ExampleChassis> EXAMPLE_CHASSIS = Registry.register(
            BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(ChassisCore.MOD_ID, "example_chassis"),
            EntityType.Builder.of(ExampleChassis::new, MobCategory.MISC).sized(1.0f, 2.3f)
                    .build(new ResourceLocation(ChassisCore.MOD_ID, "example_chassis").toString()));

    public static void register() {
        FabricDefaultAttributeRegistry.register(EXAMPLE_CHASSIS, ExampleChassis.createAttributes());
    }
}
