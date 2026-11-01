package com.nukateam.ntgl.common.foundation.init;

import com.nukateam.ntgl.Ntgl;
import com.nukateam.ntgl.common.foundation.blockentity.WorkbenchBlockEntity;
import com.nukateam.ntgl.modules.gunpack.regestry.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModTileEntities {
    public static final BlockEntityType<WorkbenchBlockEntity> WORKBENCH = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE, new ResourceLocation(Ntgl.MOD_ID, "workbench"),
            FabricBlockEntityTypeBuilder.create(WorkbenchBlockEntity::new, ModBlocks.WORKBENCH).build());

    public static void init() {}
}
