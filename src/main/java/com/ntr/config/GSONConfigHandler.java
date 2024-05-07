package com.ntr.config;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ntr.NoTextureRotations;
import me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screens.Screen;

import java.io.*;

public class GSONConfigHandler implements ConfigHandler {
    private Config config = new Config();
    private final Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();

    @Override
    public Config getConfig() {
        return config;
    }

    @Override
    public void save() {
        try {
            final File tempFile = new File(configPath().toAbsolutePath() + ".tmp");
            if (tempFile.exists()) tempFile.delete();
            try (Writer out = new FileWriter(tempFile)) {
                gson.toJson(getConfig(), out);
            }
            Files.move(tempFile, configPath().toFile());
        } catch (final Throwable e) {
            NoTextureRotations.LOGGER.error("Failed to save config", e);
        }
    }

    @Override
    public void load() {
        try {
            var configFile = configPath().toFile();
            if (configFile.exists()) {
                try (Reader reader = new FileReader(configFile)) {
                    config = gson.fromJson(reader, Config.class);
                }
            }
        } catch (final Throwable e) {
            NoTextureRotations.LOGGER.error("Failed to load config", e);
        }
    }

    @Override
    public Screen createScreen(final Screen parent) {
        if (FabricLoader.getInstance().isModLoaded("sodium")) {
            return new SodiumOptionsGUI(parent);
        }
        NoTextureRotations.LOGGER.warn("Neither YetAnotherConfigLib or Sodium are present. Unable to find a config screen provider!");
        return null;
    }
}
