package com.nukateam.ntgl.client;

import com.nukateam.ntgl.client.handlers.ClientEvents;
import com.nukateam.ntgl.client.handlers.ClientTickHandler;
import com.nukateam.ntgl.client.handlers.InputHandler;
import com.nukateam.ntgl.client.handlers.ParticleFactoryRegistry;
import com.nukateam.ntgl.client.handlers.ProjectileRenderers;
import com.nukateam.ntgl.client.input.KeyPressHandler;
import com.nukateam.ntgl.client.input.NtglKeyBinds;
import com.nukateam.ntgl.common.network.PacketHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import com.nukateam.ntgl.client.util.handler.AimingHandler;

public final class NtglClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        PacketHandler.initClient();
        ClientEvents.register();
        NtglKeyBinds.registerKeyMappings();
        com.nukateam.ntgl.client.handlers.ClientHandler.setup();
        if (com.nukateam.ntgl.Ntgl.playerAnimatorLoaded) com.nukateam.ntgl.common.util.helpers.compatibility.PlayerAnimationHelper.register();
        if (com.nukateam.ntgl.Ntgl.controllableLoaded) com.nukateam.ntgl.client.util.handler.ControllerHandler.init();
        com.nukateam.chassis_core.client.events.SetupEvents.clientSetup();
        com.nukateam.chassis_core.client.events.ModScreensEvent.register();
        com.nukateam.chassis_core.modules.example.client.events.SetupEvents.register();
        com.nukateam.chassis_core.modules.example.client.events.ModScreensClass.register();
        com.nukateam.example.client.SetupEvents.register();
        for (var item : net.minecraft.core.registries.BuiltInRegistries.ITEM) {
            if (item instanceof com.nukateam.geo.interfaces.DynamicGeoItem dynamic) {
                net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry.INSTANCE.register(item,
                        (stack, mode, matrices, buffers, light, overlay) ->
                                dynamic.getRenderer().render(net.minecraft.client.Minecraft.getInstance().player, stack, mode, matrices, buffers, null, null, light));
            }
        }
        ProjectileRenderers.registerEntityRenders();
        ParticleFactoryRegistry.register();
        net.fabricmc.fabric.api.event.player.UseBlockCallback.EVENT.register((player, world, hand, hit) ->
                world.isClientSide && com.nukateam.ntgl.client.util.handler.ClientShootingHandler.get().shouldCancelInteraction(false, hand)
                        ? net.minecraft.world.InteractionResult.FAIL : net.minecraft.world.InteractionResult.PASS);
        net.fabricmc.fabric.api.event.player.UseEntityCallback.EVENT.register((player, world, hand, entity, hit) ->
                world.isClientSide && com.nukateam.ntgl.client.util.handler.ClientShootingHandler.get().shouldCancelInteraction(false, hand)
                        ? net.minecraft.world.InteractionResult.FAIL : net.minecraft.world.InteractionResult.PASS);
        net.fabricmc.fabric.api.event.player.UseItemCallback.EVENT.register((player, world, hand) ->
                world.isClientSide && com.nukateam.ntgl.client.util.handler.ClientShootingHandler.get().shouldCancelInteraction(false, hand)
                        ? net.minecraft.world.InteractionResultHolder.fail(player.getItemInHand(hand))
                        : net.minecraft.world.InteractionResultHolder.pass(player.getItemInHand(hand)));
        ClientTickEvents.START_CLIENT_TICK.register(ClientTickHandler::startTick);
        ClientTickEvents.START_CLIENT_TICK.register(client -> com.nukateam.ntgl.CommonProxy.tick());
        ClientTickEvents.START_CLIENT_TICK.register(client -> com.nukateam.chassis_core.Global.CLIENT_TIMER.tick());
        ClientTickEvents.START_CLIENT_TICK.register(com.nukateam.ntgl.client.util.handler.ClientMeleeHandler::onClientTick);
        ClientTickEvents.START_CLIENT_TICK.register(com.nukateam.ntgl.client.util.handler.ClientShootingHandler.get()::onHandleShooting);
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            AimingHandler.get().onClientTick(client);
            if (client.level != null) client.level.players().forEach(AimingHandler.get()::onPlayerTick);
        });
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> AimingHandler.get().onDisconnect());
        com.nukateam.ntgl.common.event.WeaponEvents.FIRE_POST.register(com.nukateam.ntgl.client.util.handler.CrosshairHandler.get()::onGunFired);
        com.nukateam.ntgl.common.event.WeaponEvents.FIRE_POST.register(com.nukateam.ntgl.client.util.handler.RecoilHandler.get()::onGunFire);
        com.nukateam.ntgl.common.event.WeaponEvents.FIRE_POST.register(com.nukateam.ntgl.client.util.handler.WeaponRenderingHandler.get()::onGunFire);
        ClientTickEvents.END_CLIENT_TICK.register(com.nukateam.ntgl.client.util.handler.CrosshairHandler.get()::onClientTick);
        ClientTickEvents.END_CLIENT_TICK.register(com.nukateam.ntgl.client.util.handler.WeaponRenderingHandler.get()::onTick);
        ClientTickEvents.END_CLIENT_TICK.register(com.nukateam.ntgl.client.util.handler.SoundHandler.get()::deafenPlayer);
        ClientTickEvents.END_CLIENT_TICK.register(com.nukateam.ntgl.client.handlers.ClientHandler::clientTick);
        ClientTickEvents.END_CLIENT_TICK.register(ClientTickHandler::endTick);
        ClientTickEvents.END_CLIENT_TICK.register(com.nukateam.ntgl.client.util.handler.ClientReloadHandler.get()::onClientTick);
        ClientTickEvents.END_CLIENT_TICK.register(com.nukateam.ntgl.client.util.handler.ClientEquipHandler.get()::onPostClientTick);
        ClientTickEvents.END_CLIENT_TICK.register(com.nukateam.ntgl.client.util.handler.ClientThrowHandler::onPostClientTick);
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.level != null) client.level.players().forEach(com.nukateam.ntgl.common.handlers.PlayerEventHandler::onPlayerTick);
        });
        ClientTickEvents.END_CLIENT_TICK.register(InputHandler::onClientTick);
        ClientTickEvents.END_CLIENT_TICK.register(KeyPressHandler::onClientTick);
    }
}
