package com.nukateam.ntgl.common.event;

import com.nukateam.ntgl.common.data.WeaponData;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public class AttachmentEvent {
    @FunctionalInterface
    public interface SlotUpdate {
        boolean onUpdate(SlotUpdateEvent event);
    }

    @FunctionalInterface
    public interface ContainerUpdate {
        boolean onUpdate(ContainerUpdateEvent event);
    }

    public static final Event<SlotUpdate> SLOT_UPDATE = EventFactory.createArrayBacked(SlotUpdate.class, listeners -> event -> {
        for (var listener : listeners) if (listener.onUpdate(event)) return true;
        return false;
    });

    public static final Event<ContainerUpdate> CONTAINER_UPDATE = EventFactory.createArrayBacked(ContainerUpdate.class, listeners -> event -> {
        for (var listener : listeners) if (listener.onUpdate(event)) return true;
        return false;
    });
    private final WeaponData weaponData;
    private final AbstractContainerMenu containerMenu;


    public static class SlotUpdateEvent extends AttachmentEvent{
        private final ItemStack oldStack;
        private final ItemStack newStack;

        public SlotUpdateEvent(AbstractContainerMenu containerMenu, WeaponData weaponData, ItemStack oldStack, ItemStack newStack) {
            super(weaponData, containerMenu);
            this.oldStack = oldStack;
            this.newStack = newStack;
        }

        public ItemStack getOldStack() {
            return oldStack;
        }

        public ItemStack getNewStack() {
            return newStack;
        }
    }

    public static class ContainerUpdateEvent extends AttachmentEvent{
        public ContainerUpdateEvent(AbstractContainerMenu containerMenu, WeaponData weaponData) {
            super(weaponData, containerMenu);
        }
    }

    public AttachmentEvent(WeaponData weaponData, AbstractContainerMenu containerMenu) {
        this.weaponData = weaponData;
        this.containerMenu = containerMenu;
    }

    public WeaponData getWeaponData() {
        return weaponData;
    }

    public AbstractContainerMenu getContainerMenu() {
        return containerMenu;
    }
}
