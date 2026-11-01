package com.nukateam.chassis_core.common.input;

import com.nukateam.chassis_core.common.events.CommonInputEvent;
import net.minecraft.world.entity.player.Player;

import static com.nukateam.chassis_core.common.input.InputKey.JUMP;
import static com.nukateam.chassis_core.common.input.KeyAction.PRESS;
import static com.nukateam.chassis_core.common.input.KeyAction.REPEAT;
import static com.nukateam.chassis_core.common.util.helpers.PlayerUtils.getEntityChassis;
import static com.nukateam.chassis_core.common.util.helpers.PlayerUtils.isWearingChassis;

public class CommonInputHandler {
    public static void onKeyInput(InputKey key, KeyAction action, Player player) {
        CommonInputEvent.EVENT.invoker().onInput(key, action, player);
        if (key != null && isWearingChassis(player) && (action == PRESS || action == REPEAT) && key == JUMP) {
            getEntityChassis(player).jump();
        }
    }
}
