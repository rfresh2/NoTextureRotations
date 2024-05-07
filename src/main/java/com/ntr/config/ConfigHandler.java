package com.ntr.config;

import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public interface ConfigHandler {
    Config getConfig();
    void save();
    void load();

    default Path configPath() {
        return FabricLoader.getInstance().getConfigDir().resolve("no-texture-rotations.json");
    }
}
