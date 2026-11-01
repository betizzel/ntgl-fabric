package com.nukateam.chassis_core.modules.example.common.registery;

import com.nukateam.chassis_core.ChassisCore;
import com.nukateam.chassis_core.modules.example.common.container.ExampleChassisMenu;
import com.nukateam.chassis_core.modules.example.common.container.ExampleChassisStationMenu;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.world.inventory.MenuType;

public class ContainerRegistry {
    public static MenuType<ExampleChassisMenu> EXAMPLE_CHASSIS_MENU;
    public static MenuType<ExampleChassisStationMenu> EXAMPLE_STATION_MENU;

    public static void register() {
        EXAMPLE_CHASSIS_MENU = Registry.register(
                BuiltInRegistries.MENU, new ResourceLocation(ChassisCore.MOD_ID, "example_chassis_menu"),
                new ExtendedScreenHandlerType<>(ExampleChassisMenu::new));
        EXAMPLE_STATION_MENU = Registry.register(
                BuiltInRegistries.MENU, new ResourceLocation(ChassisCore.MOD_ID, "example_chassis_station_menu"),
                new ExtendedScreenHandlerType<>(ExampleChassisStationMenu::new));
    }
}
