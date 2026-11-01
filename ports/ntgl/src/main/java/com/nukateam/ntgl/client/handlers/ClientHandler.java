package com.nukateam.ntgl.client.handlers;

import com.nukateam.ntgl.client.registry.AnimationRegistry;
import com.nukateam.ntgl.client.render.screen.AttachmentScreen;
import com.nukateam.ntgl.client.render.screen.WorkbenchScreen;
import com.nukateam.ntgl.common.debug.IEditorMenu;
import com.nukateam.ntgl.common.debug.screen.EditorScreen;
import com.nukateam.ntgl.common.foundation.init.ModContainers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.InteractionHand;

public class ClientHandler {
    public static final int INSPECTION_DURATION = 60;
    public static final int INSPECTION_OFFSET = 5;
    private static int inspectionTimerRight;
    private static int inspectionTimerLeft;

    public static void setup() {
        MenuScreens.register(ModContainers.WORKBENCH, WorkbenchScreen::new);
        MenuScreens.register(ModContainers.ATTACHMENTS, AttachmentScreen::new);
        AnimationRegistry.register();
    }

    public static void clientTick(Minecraft client) {
        if (inspectionTimerRight == INSPECTION_DURATION - 2) inspectionTimerLeft = INSPECTION_DURATION;
        if (inspectionTimerRight > 0) inspectionTimerRight--;
        if (inspectionTimerLeft > 0) inspectionTimerLeft--;
    }

    public static void resetInspectionTimer() { inspectionTimerRight = INSPECTION_DURATION; }
    public static int getInspectionTicks(InteractionHand hand) { return hand == InteractionHand.MAIN_HAND ? inspectionTimerRight : inspectionTimerLeft; }
    public static boolean isInspecting() { return inspectionTimerRight > 0 || inspectionTimerLeft > 0; }
    public static int getMaxInspectionTicks() { return INSPECTION_DURATION; }
    public static Screen createEditorScreen(IEditorMenu menu) { return new EditorScreen(Minecraft.getInstance().screen, menu); }
    public static int getInspectionTimerRight() { return inspectionTimerRight; }
    public static void setInspectionTimerRight(int ticks) { inspectionTimerRight = ticks; }
}
