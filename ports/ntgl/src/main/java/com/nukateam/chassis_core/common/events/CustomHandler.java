package com.nukateam.chassis_core.common.events;

public class CustomHandler {
    public static void register() {
        ContainerChangedEvent.EVENT.register(System.out::println);
    }
}
