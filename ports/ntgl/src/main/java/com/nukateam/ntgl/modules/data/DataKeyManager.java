package com.nukateam.ntgl.modules.data;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import com.nukateam.ntgl.common.network.PacketHandler;
import com.nukateam.ntgl.modules.data.message.S2CMessageUpdateEntityData;


import java.util.HashMap;

public class DataKeyManager {
    private static DataKeyManager instance = null;
    private final HashMap<Integer, DataKey> dataKeys = new HashMap<>();
    private int id = 0;

    private DataKeyManager(){}

    public static DataKeyManager getInstance(){
        if(instance == null)
            instance = new DataKeyManager();
        return instance;
    }

    public void registerKey(DataKey dataKey){
        dataKeys.put(id++, dataKey);
    }

    public void setData(Boolean data, int dataKeyId, int entityId){
        if(dataKeys.containsKey(dataKeyId)) {
            dataKeys.get(dataKeyId).setValue(entityId, data);
        }
    }

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            var entries = new HashMap<DataEntry, S2CMessageUpdateEntityData.EntityData>();

            DataKeyManager.getInstance().dataKeys.forEach((dataKeyId, dataKey) -> {
                dataKey.getData().forEach((entityId, dataEntry) -> {
                    if (dataEntry.isPendingSync()) {
                        dataEntry.setPendingSync(false);
                        entries.put(dataEntry, new S2CMessageUpdateEntityData.EntityData(entityId, dataKeyId));
                    }
                });
            });

            if (!entries.isEmpty()) {
                PacketHandler.getPlayChannel().sendToAll(new S2CMessageUpdateEntityData(entries));
            }
        });
    }
}
