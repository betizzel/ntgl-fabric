package com.nukateam.ntgl.client.render.screen;

import com.nukateam.ntgl.Config;
import com.nukateam.ntgl.client.util.enums.SwayType;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.ForgeConfigSpec;

public final class NtglConfigScreen {
    private NtglConfigScreen() {}

    public static Screen create(Screen parent) {
        var builder = ConfigBuilder.create().setParentScreen(parent).setTitle(Component.literal("NTGL Client Settings"));
        builder.setSavingRunnable(Config::saveClientConfig);
        var entry = builder.entryBuilder();
        var client = Config.CLIENT;

        var general = builder.getOrCreateCategory(Component.literal("General"));
        addBoolean(general, entry, "Hide config button", client.hideConfigButton, false);
        general.addEntry(entry.startEnumSelector(Component.literal("Button alignment"), ButtonAlignment.class, client.buttonAlignment.get())
                .setDefaultValue(ButtonAlignment.RIGHT).setSaveConsumer(client.buttonAlignment::set).build());

        var sounds = builder.getOrCreateCategory(Component.literal("Sounds"));
        addBoolean(sounds, entry, "Play headshot sound", client.sounds.playSoundWhenHeadshot, true);
        addString(sounds, entry, "Headshot sound", client.sounds.headshotSound, "minecraft:entity.player.attack.knockback");
        addBoolean(sounds, entry, "Play critical hit sound", client.sounds.playSoundWhenCritical, true);
        addString(sounds, entry, "Critical hit sound", client.sounds.criticalSound, "minecraft:entity.player.attack.crit");
        addDouble(sounds, entry, "Impact sound distance", client.sounds.impactSoundDistance, 32, 0, 32);

        var display = builder.getOrCreateCategory(Component.literal("Display"));
        addBoolean(display, entry, "Old animations", client.display.oldAnimations, false);
        addString(display, entry, "Crosshair", client.display.crosshair, "minecraft:default");
        addBoolean(display, entry, "Cooldown indicator", client.display.cooldownIndicator, true);
        addBoolean(display, entry, "Weapon sway", client.display.weaponSway, true);
        addDouble(display, entry, "Sway sensitivity", client.display.swaySensitivity, 0.3, 0, 1);
        display.addEntry(entry.startEnumSelector(Component.literal("Sway type"), SwayType.class, client.display.swayType.get())
                .setDefaultValue(SwayType.DRAG).setSaveConsumer(client.display.swayType::set).build());
        addBoolean(display, entry, "Camera roll", client.display.cameraRollEffect, true);
        addDouble(display, entry, "Camera roll angle", client.display.cameraRollAngle, 1.5, 0, 45);
        addBoolean(display, entry, "Restrict camera roll to weapons", client.display.restrictCameraRollToWeapons, true);
        addBoolean(display, entry, "Sprint animation", client.display.sprintAnimation, true);
        addDouble(display, entry, "Bobbing intensity", client.display.bobbingIntensity, 1, 0, 2);

        var particles = builder.getOrCreateCategory(Component.literal("Particles"));
        addInt(particles, entry, "Minimum bullet hole lifetime", client.particle.bulletHoleLifeMin, 150, 0, Integer.MAX_VALUE);
        addInt(particles, entry, "Maximum bullet hole lifetime", client.particle.bulletHoleLifeMax, 200, 0, Integer.MAX_VALUE);
        addDouble(particles, entry, "Bullet hole fade threshold", client.particle.bulletHoleFadeThreshold, 0.98, 0, 1);
        addBoolean(particles, entry, "Blood", client.particle.enableBlood, true);
        addBoolean(particles, entry, "Death effects", client.particle.enableDeathFX, true);
        addDouble(particles, entry, "Impact particle distance", client.particle.impactParticleDistance, 32, 0, 64);

        var controls = builder.getOrCreateCategory(Component.literal("Controls"));
        addDouble(controls, entry, "Aim sensitivity", client.controls.aimDownSightSensitivity, 0.75, 0, 1);
        addBoolean(controls, entry, "Flip controls", client.controls.flipControls, false);
        return builder.build();
    }

    private static void addBoolean(ConfigCategory category, ConfigEntryBuilder entry, String label,
                                   ForgeConfigSpec.BooleanValue value, boolean defaultValue) {
        category.addEntry(entry.startBooleanToggle(Component.literal(label), value.get()).setDefaultValue(defaultValue)
                .setSaveConsumer(value::set).build());
    }

    private static void addString(ConfigCategory category, ConfigEntryBuilder entry, String label,
                                  ForgeConfigSpec.ConfigValue<String> value, String defaultValue) {
        category.addEntry(entry.startStrField(Component.literal(label), value.get()).setDefaultValue(defaultValue)
                .setSaveConsumer(value::set).build());
    }

    private static void addInt(ConfigCategory category, ConfigEntryBuilder entry, String label,
                               ForgeConfigSpec.IntValue value, int defaultValue, int min, int max) {
        category.addEntry(entry.startIntField(Component.literal(label), value.get()).setDefaultValue(defaultValue)
                .setMin(min).setMax(max).setSaveConsumer(value::set).build());
    }

    private static void addDouble(ConfigCategory category, ConfigEntryBuilder entry, String label,
                                  ForgeConfigSpec.DoubleValue value, double defaultValue, double min, double max) {
        category.addEntry(entry.startDoubleField(Component.literal(label), value.get()).setDefaultValue(defaultValue)
                .setMin(min).setMax(max).setSaveConsumer(value::set).build());
    }
}
