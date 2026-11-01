package com.nukateam.ntgl.client.input;

import com.nukateam.ntgl.client.util.handler.ClientReloadHandler;
import com.nukateam.ntgl.common.data.WeaponData;
import com.nukateam.ntgl.common.data.holders.WeaponMode;
import com.nukateam.ntgl.common.util.util.WeaponModifierHelper;
import com.nukateam.ntgl.modules.wheel.ActionWheel;
import com.nukateam.ntgl.modules.wheel.ActionWheelManager;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;

import static com.nukateam.ntgl.client.util.handler.ClientShootingHandler.isInGame;

public class KeyPressHandler {
    private static final ArrayList<KeyCommand> commands = new ArrayList<>();

    public static void onClientTick(Minecraft client) {
        var player = client.player;
        if (isInGame() && player != null) {
            for (var command : commands){
                if (command.getKey().isDown()){
                    if(!command.keyPressed) {
                        command.keyPressed = true;
                        command.onPress();
                    }
                }
                else {
                    if (command.keyPressed){
                        command.keyPressed = false;
                        command.onRelease();
                    }
                }
            }
        }
    }

    public static void addCommand(KeyCommand command){
        commands.add(command);
    }
}
