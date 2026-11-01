package com.nukateam.ntgl.common.foundation.event;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public class WorkbenchCraftEvent {
    @FunctionalInterface
    public interface Callback {
        boolean onCraft(WorkbenchCraftEvent event);
    }

    /** Returns true to reject crafting; listeners may set a rejection message. */
    public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, listeners -> event -> {
        for (var listener : listeners) if (listener.onCraft(event)) return true;
        return false;
    });

    private final Player player;
    private final ItemStack outputItem;
    private Component rejectionMessage;

    public WorkbenchCraftEvent(Player player, ItemStack outputItem) {
        this.player = player;
        this.outputItem = outputItem;
    }

    public Player getPlayer() {
        return player;
    }

    public ItemStack getOutputItem() {
        return outputItem;
    }

    public Component getRejectionMessage() {
        return rejectionMessage;
    }

    public void setRejectionMessage(Component rejectionMessage) {
        this.rejectionMessage = rejectionMessage;
    }
}
