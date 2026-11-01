package com.nukateam.chassis_core.common.foundation.registery;

import com.nukateam.chassis_core.ChassisCore;
import com.nukateam.chassis_core.common.foundation.container.menu.DynamicChassisMenu;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;

public class ContainerRegistry {
    public static MenuType<DynamicChassisMenu> CHASSIS_MENU;

    public static void register() {
        CHASSIS_MENU = Registry.register(BuiltInRegistries.MENU,
                new ResourceLocation(ChassisCore.MOD_ID, "chassis_menu"),
                new ExtendedScreenHandlerType<>(DynamicChassisMenu::new));
    }
}
