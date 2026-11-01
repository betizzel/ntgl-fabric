package com.nukateam.ntgl.client.event;

import com.nukateam.ntgl.client.render.hud.WeaponHud;
import com.nukateam.ntgl.client.render.hud.cache.GunHudCache;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.InteractionHand;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public class GunHudEvent {
    public static final Event<Listener> EVENT = EventFactory.createArrayBacked(Listener.class, listeners -> event -> {
        boolean cancelled = false;
        for (Listener listener : listeners) cancelled |= listener.onRender(event);
        return cancelled;
    });

    @FunctionalInterface
    public interface Listener {
        boolean onRender(GunHudEvent event);
    }
    private final WeaponHud weaponHud;
    private final InteractionHand hand;
    private final GuiGraphics graphics;
    private final GunHudCache cache;
    private final GunHudEvent.Phase phase;


    public GunHudEvent(WeaponHud weaponHud, InteractionHand hand, GuiGraphics graphics, GunHudCache cache, Phase phase) {
        this.weaponHud = weaponHud;
        this.hand = hand;
        this.graphics = graphics;
        this.cache = cache;
        this.phase = phase;
    }

    public InteractionHand getHand() {
        return hand;
    }

    public GunHudCache getCache() {
        return cache;
    }

    public GuiGraphics getGraphics() {
        return graphics;
    }

    public Phase getRenderPhase() {
        return phase;
    }

    public WeaponHud getGunHud() {
        return weaponHud;
    }

    public enum Phase {
        START, END;
    }
}
