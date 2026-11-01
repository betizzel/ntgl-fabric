package com.nukateam.example.common.registery;

import com.nukateam.ntgl.Ntgl;
import com.nukateam.ntgl.common.util.helpers.RegistrationHelper;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ModItemTabs {
    public static void register() {
        if (Ntgl.isDebugging()) {
            Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, new ResourceLocation(Ntgl.MOD_ID, "ntgl_items"),
                    FabricItemGroup.builder().icon(() -> new ItemStack(ExampleWeapons.ROUND10MM))
                            .title(Component.translatable("itemGroup.ntgl.example"))
                            .displayItems((params, output) -> {
                                for (var item : ExampleWeapons.items()) {
                                    RegistrationHelper.registerGunOrDefault(output, item);
                                }
                            }).build());
        }
    }
}
