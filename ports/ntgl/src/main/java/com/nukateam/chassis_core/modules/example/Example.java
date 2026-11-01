package com.nukateam.chassis_core.modules.example;

import com.nukateam.chassis_core.modules.example.common.registery.ChassisArmorItems;
import com.nukateam.chassis_core.modules.example.common.registery.ContainerRegistry;
import com.nukateam.chassis_core.modules.example.common.registery.EntityTypes;

public class Example {
    public static void init() {
        ContainerRegistry.register();
        EntityTypes.register();
        ChassisArmorItems.register();
    }
}
