package com.nukateam.ntgl.modules.gunpack.resource;

import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class NTGLPackManager {
    private static final List<Path> RESOURCE_PACKS = new ArrayList<>();
    private static final List<Path> DATA_PACKS = new ArrayList<>();

    public static synchronized void scanPacks() {
        var packsDir = FabricLoader.getInstance().getGameDir().resolve("ntgl");

        try {
            if (!Files.exists(packsDir)) {
                Files.createDirectories(packsDir);
            }

            RESOURCE_PACKS.clear();
            DATA_PACKS.clear();

            try (var packs = Files.list(packsDir)) {
                packs.forEach(NTGLPackManager::processPack);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processPack(Path packPath) {
        try {
            var isZip = Files.isRegularFile(packPath) && packPath.toString().endsWith(".zip");
            var isFolder = Files.isDirectory(packPath);

            if (!isZip && !isFolder) return;

            var fs = isZip ?
                    FileSystems.newFileSystem(packPath, (ClassLoader) null) :
                    FileSystems.getDefault();

            var root = isZip ? fs.getPath("/") : packPath;

            var hasAssets = Files.exists(root.resolve("assets"));
            var hasData = Files.exists(root.resolve("data"));

            if (hasAssets) RESOURCE_PACKS.add(packPath);
            if (hasData) DATA_PACKS.add(packPath);

            if (isZip) fs.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void addPacks(PackType packType, Consumer<Pack> consumer) {
        List<Path> packs = packType == PackType.CLIENT_RESOURCES ? RESOURCE_PACKS : DATA_PACKS;
        String type = packType == PackType.CLIENT_RESOURCES ? "assets" : "data";
        for (Path packPath : packs) {
            Pack.ResourcesSupplier supplier = (packId) -> {
                try {
                    return new NTGLPackResources(packPath, type, packId);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to create pack resources for " + packPath, e);
                }
            };

            var pack = Pack.readMetaAndCreate(
                    "ntgl/" + packPath.getFileName(),
                    Component.literal("NTGL Pack: " + packPath.getFileName()),
                    true, supplier, packType, Pack.Position.TOP, PackSource.BUILT_IN);
            if (pack != null) consumer.accept(pack);
        }
    }
}