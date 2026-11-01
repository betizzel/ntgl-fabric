package com.nukateam.ntgl.modules.gunpack.data;

import com.google.gson.JsonObject;
import com.nukateam.ntgl.common.foundation.item.AmmoItem;
import com.nukateam.ntgl.common.foundation.item.WeaponItem;
import com.nukateam.ntgl.common.foundation.item.attachment.GenericAttachmentItem;
import com.nukateam.ntgl.common.util.helpers.RegistrationHelper;
import com.nukateam.ntgl.modules.gunpack.GunPackModule;
import com.nukateam.ntgl.modules.gunpack.regestry.ModBlocks;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import static net.minecraft.util.datafix.fixes.BlockEntitySignTextStrictJsonFix.GSON;

public class GunRegisterer {
    private static final Pattern RECIPE_PATTERN = Pattern.compile("^data/([^/]+)/recipes/([^/]+\\.json)$");
    private static final Map<String, List<Item>> ITEMS = new LinkedHashMap<>();
    private static boolean hasValidRecipe;

    public static void init() {
        Path directory = FabricLoader.getInstance().getGameDir().resolve("ntgl");
        try {
            Files.createDirectories(directory);
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory, "*.zip")) {
                for (Path path : stream) processPack(path);
            }
        } catch (IOException e) {
            GunPackModule.LOGGER.error("Error scanning gun packs in {}", directory, e);
        }
        registerTabs();
    }

    private static void processPack(Path path) {
        try (ZipFile zip = new ZipFile(path.toFile())) {
            ZipEntry manifest = zip.getEntry("registry.json");
            if (manifest != null) {
                try (var reader = new InputStreamReader(zip.getInputStream(manifest), StandardCharsets.UTF_8)) {
                    JsonObject json = GSON.fromJson(reader, JsonObject.class);
                    registerItems(json, com.nukateam.ntgl.modules.constants.Paths.WEAPONS, id -> new WeaponItem(new Item.Properties().stacksTo(1)));
                    registerItems(json, com.nukateam.ntgl.modules.constants.Paths.ATTACHMENTS, id -> new GenericAttachmentItem(new Item.Properties().stacksTo(1)));
                    registerItems(json, com.nukateam.ntgl.modules.constants.Paths.AMMO, id -> new AmmoItem(new Item.Properties()));
                }
            }
            var entries = zip.entries();
            while (!hasValidRecipe && entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (!entry.isDirectory() && RECIPE_PATTERN.matcher(entry.getName()).matches()) {
                    try (var reader = new BufferedReader(new InputStreamReader(zip.getInputStream(entry), StandardCharsets.UTF_8))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            if (line.contains("\"type\": \"ntgl:workbench\"")) {
                                hasValidRecipe = true;
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            GunPackModule.LOGGER.error("Error processing pack {}", path, e);
        }
    }

    private static void registerItems(JsonObject manifest, String key, Function<ResourceLocation, Item> factory) {
        var entries = manifest.getAsJsonArray(key);
        if (entries == null) return;
        entries.forEach(entry -> {
            ResourceLocation id = ResourceLocation.tryParse(entry.getAsString());
            if (id != null && !BuiltInRegistries.ITEM.containsKey(id)) {
                Item item = Registry.register(BuiltInRegistries.ITEM, id, factory.apply(id));
                ITEMS.computeIfAbsent(id.getNamespace(), unused -> new ArrayList<>()).add(item);
            }
        });
    }

    private static void registerTabs() {
        if (hasValidRecipe) {
            Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, new ResourceLocation(GunPackModule.MOD_ID, "ntgl_blocks"),
                    FabricItemGroup.builder().icon(() -> new ItemStack(ModBlocks.WORKBENCH))
                            .title(Component.translatable("itemGroup." + GunPackModule.MOD_ID + ".blocks"))
                            .displayItems((params, output) -> output.accept(ModBlocks.WORKBENCH)).build());
        }
        ITEMS.forEach((namespace, items) -> Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,
                new ResourceLocation(GunPackModule.MOD_ID, namespace),
                FabricItemGroup.builder().icon(() -> new ItemStack(items.get(0)))
                        .title(Component.translatable("itemGroup." + namespace))
                        .displayItems((params, output) -> items.forEach(item -> RegistrationHelper.registerGunOrDefault(output, item)))
                        .build()));
    }
}
