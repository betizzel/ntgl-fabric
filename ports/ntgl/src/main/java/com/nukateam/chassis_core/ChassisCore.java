package com.nukateam.chassis_core;

import com.nukateam.chassis_core.common.foundation.registery.ContainerRegistry;
import com.nukateam.chassis_core.common.events.CustomHandler;
import com.nukateam.chassis_core.common.foundation.entity.Chassis;
import com.nukateam.chassis_core.common.network.packet.S2CInventoryPacket;
import com.nukateam.chassis_core.common.util.helpers.InventoryHelper;
import com.nukateam.ntgl.common.network.PacketHandler;
import net.fabricmc.fabric.api.networking.v1.EntityTrackingEvents;
import com.nukateam.chassis_core.modules.example.Example;
import com.nukateam.ntgl.Ntgl;
import org.slf4j.Logger;

public class ChassisCore {
    public static final String MOD_ID = Ntgl.MOD_ID;
    public static final Logger LOGGER = Ntgl.LOGGER;

    public ChassisCore() {
        CustomHandler.register();
        EntityTrackingEvents.START_TRACKING.register((entity, player) -> {
            if (entity instanceof Chassis chassis) {
                PacketHandler.getPlayChannel().sendToPlayer(() -> player,
                        new S2CInventoryPacket(chassis.getId(), InventoryHelper.serializeInventory(chassis.inventory)));
            }
        });
        ContainerRegistry.register();
        Example.init();
    }
}
