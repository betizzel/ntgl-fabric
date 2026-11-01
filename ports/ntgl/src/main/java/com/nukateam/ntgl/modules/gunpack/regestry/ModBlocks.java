package com.nukateam.ntgl.modules.gunpack.regestry;

import com.nukateam.ntgl.Ntgl;
import com.nukateam.ntgl.common.foundation.block.WorkbenchBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

public class ModBlocks {
    public static final Block WORKBENCH = registerBlock("workbench", new WorkbenchBlock(Block.Properties.of()
            .strength(1.5F).sound(SoundType.METAL).mapColor(MapColor.METAL).lightLevel(state -> 6)));

    private static <T extends Block> T registerBlock(String name, T block) {
        ResourceLocation id = new ResourceLocation(Ntgl.MOD_ID, name);
        Registry.register(BuiltInRegistries.BLOCK, id, block);
        Registry.register(BuiltInRegistries.ITEM, id, new BlockItem(block, new Item.Properties()));
        return block;
    }

    public static void init() {}
}
