package com.nukateam.chassis_core.modules.example.client.events;

import com.nukateam.chassis_core.modules.example.client.screen.ExampleChassisScreen;
import com.nukateam.chassis_core.modules.example.client.screen.ExampleChassisStationScreen;
import com.nukateam.chassis_core.modules.example.common.registery.ContainerRegistry;
import net.minecraft.client.gui.screens.MenuScreens;

public class ModScreensClass {
    public static void register() {
        MenuScreens.register(ContainerRegistry.EXAMPLE_CHASSIS_MENU, ExampleChassisScreen::new);
        MenuScreens.register(ContainerRegistry.EXAMPLE_STATION_MENU, ExampleChassisStationScreen::new);
    }
}
