package com.nukateam.ntgl.common.foundation.init;

import com.nukateam.ntgl.Ntgl;
import com.nukateam.ntgl.common.foundation.blockentity.WorkbenchBlockEntity;
import com.nukateam.ntgl.common.foundation.container.AttachmentContainer;
import com.nukateam.ntgl.common.foundation.container.WorkbenchContainer;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

public class ModContainers {
    public static final MenuType<WorkbenchContainer> WORKBENCH = Registry.register(
            BuiltInRegistries.MENU, new ResourceLocation(Ntgl.MOD_ID, "workbench"),
            new ExtendedScreenHandlerType<>((windowId, inventory, data) -> {
                WorkbenchBlockEntity workbench = (WorkbenchBlockEntity) inventory.player.level().getBlockEntity(data.readBlockPos());
                return new WorkbenchContainer(windowId, inventory, workbench);
            }));

    public static final MenuType<AttachmentContainer> ATTACHMENTS = Registry.register(
            BuiltInRegistries.MENU, new ResourceLocation(Ntgl.MOD_ID, "attachments"),
            new MenuType<>(AttachmentContainer::new, FeatureFlags.DEFAULT_FLAGS));

    public static void init() {}
}
