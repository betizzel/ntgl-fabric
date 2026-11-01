package com.nukateam.ntgl;

public class CommonProxy {
    public static void tick() {
        ClientProxy.damageTypes.values().removeIf(value -> value.ticks-- <= 0);
    }
}
