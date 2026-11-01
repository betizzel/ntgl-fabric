package com.nukateam.chassis_core.client.events;

import com.nukateam.chassis_core.common.foundation.registery.ContainerRegistry;
import com.nukateam.chassis_core.client.gui.screen.DynamicChassisScreen;
import net.minecraft.client.gui.screens.MenuScreens;

public class ModScreensEvent {
    public static void register() {
        MenuScreens.register(ContainerRegistry.CHASSIS_MENU, DynamicChassisScreen::new);
    }
}
