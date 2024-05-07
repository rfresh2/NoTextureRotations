package com.ntr.config;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;

public interface ConfigHandler {
    Config getConfig();
    void save();
    void load();
    @Nullable Screen createScreen(Screen parent);

    default Path configPath() {
        return FabricLoader.getInstance().getConfigDir().resolve("no-texture-rotations.json");
    }
}
