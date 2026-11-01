package com.nukateam.ntgl;

import com.mojang.logging.LogUtils;
import com.nukateam.chassis_core.ChassisCore;
import com.nukateam.example.common.registery.EntityTypes;
import com.nukateam.example.common.registery.ExampleWeapons;
import com.nukateam.example.common.registery.ModItemTabs;
import com.nukateam.ntgl.client.settings.NtglOptions;
import com.nukateam.ntgl.common.data.holders.AnimationType;
import com.nukateam.ntgl.common.debug.Debug;
import com.nukateam.ntgl.common.foundation.crafting.ModRecipeType;
import com.nukateam.ntgl.common.foundation.crafting.WorkbenchIngredient;
import com.nukateam.ntgl.common.foundation.effect.IncurableMilk;
import com.nukateam.ntgl.common.foundation.init.*;
import com.nukateam.ntgl.common.handlers.GunEventHandler;
import com.nukateam.ntgl.common.handlers.EntityEvents;
import com.nukateam.ntgl.common.handlers.GunBehaviorManager;
import com.nukateam.ntgl.common.network.PacketHandler;
import com.nukateam.ntgl.common.registry.AmmoHolders;
import com.nukateam.ntgl.common.registry.ProjectileRegistry;
import com.nukateam.ntgl.common.util.helpers.compatibility.PlayerReviveHelper;
import com.nukateam.ntgl.modules.data.DataKeyManager;
import com.nukateam.ntgl.modules.datapack.handlers.NetworkManagerHandler;
import com.nukateam.ntgl.common.util.trackers.EquipTracker;
import com.nukateam.ntgl.common.util.trackers.EntityReloadTracker;
import com.nukateam.ntgl.common.util.trackers.MeleeTracker;
import com.nukateam.ntgl.common.util.trackers.ReloadTracker;
import com.nukateam.ntgl.common.util.trackers.SpreadTracker;
import com.nukateam.ntgl.common.util.trackers.ThrowingTracker;
import com.nukateam.ntgl.common.util.managers.BoundingBoxManager;
import com.nukateam.ntgl.common.util.util.DelayedTask;
import com.nukateam.ntgl.modules.gunpack.GunPackModule;
import com.nukateam.ntgl.modules.gunpack.regestry.ModBlocks;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;

public class Ntgl implements ModInitializer {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "ntgl";

    public static boolean controllableLoaded;
    public static boolean backpackedLoaded;
    public static boolean sophisticatedLoaded;
    public static boolean travelersLoaded;
    public static boolean yyzBackpackLoaded;
    public static boolean curiosLoaded;
    public static boolean playerReviveLoaded;
    public static boolean playerAnimatorLoaded;
    public static boolean subtleEffectsLoaded;

    @Override
    public void onInitialize() {
        ForgeConfigRegistry.INSTANCE.register(MOD_ID, ModConfig.Type.CLIENT, Config.clientSpec);
        ForgeConfigRegistry.INSTANCE.register(MOD_ID, ModConfig.Type.COMMON, Config.commonSpec);
        ForgeConfigRegistry.INSTANCE.register(MOD_ID, ModConfig.Type.SERVER, Config.serverSpec);

        var loader = FabricLoader.getInstance();
        curiosLoaded = loader.isModLoaded("trinkets");
        controllableLoaded = loader.isModLoaded("controllable");
        backpackedLoaded = loader.isModLoaded("backpacked");
        sophisticatedLoaded = loader.isModLoaded("sophisticatedbackpacks");
        travelersLoaded = loader.isModLoaded("travelersbackpack");
        yyzBackpackLoaded = loader.isModLoaded("yyzsbackpack");
        playerReviveLoaded = PlayerReviveHelper.initialize(loader.isModLoaded("playerrevive"));
        playerAnimatorLoaded = loader.isModLoaded("playeranimator");
        subtleEffectsLoaded = loader.isModLoaded("subtle_effects");

        ModBlocks.init();
        ModContainers.init();
        ModEffects.init();
        IncurableMilk.register();
        Projectiles.init();
        ExampleWeapons.register();
        ModItemTabs.register();
        ModRecipeType.init();
        ModParticleTypes.init();
        ModRecipeSerializers.init();
        WorkbenchIngredient.register();
        ModSounds.init();
        ModTileEntities.init();
        ModEntityTypes.init();
        EntityTypes.register();
        GunPackModule.init();
        NtglGameEvents.init();
        new ChassisCore();

        AmmoHolders.register();
        AnimationType.register();
        ModSyncedDataKeys.register();
        PacketHandler.init();
        NetworkManagerHandler.register();
        EquipTracker.register();
        EntityReloadTracker.register();
        MeleeTracker.register();
        ReloadTracker.register();
        SpreadTracker.register();
        ThrowingTracker.register();
        BoundingBoxManager.register();
        DelayedTask.init();
        Debug.register();
        DataKeyManager.register();
        GunEventHandler.register();
        EntityEvents.register();
        GunBehaviorManager.register();
        ProjectileRegistry.registerProjectiles();
    }

    public static boolean isDebugging() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    public static NtglOptions getOptions() {
        return NtglOptions.getInstance();
    }

    public static ResourceLocation ntglResource(String name) {
        return ResourceLocation.tryBuild(MOD_ID, name);
    }
}
