package com.ntr.config;

import com.ntr.NoTextureRotations;
import me.jellysquid.mods.sodium.client.gui.options.storage.OptionStorage;

public class SodiumOptionStorage implements OptionStorage<Config> {
    public static SodiumOptionStorage INSTANCE = new SodiumOptionStorage();

    @Override
    public Config getData() {
        return NoTextureRotations.config.getConfig();
    }

    @Override
    public void save() {
        NoTextureRotations.config.save();
    }
}
