package com.ntr.config;

import com.ntr.NoTextureRotations;
import net.caffeinemc.mods.sodium.client.gui.options.storage.OptionStorage;

public class Sodium6OptionStorage implements OptionStorage<Config> {
    public static Sodium6OptionStorage INSTANCE = new Sodium6OptionStorage();

    @Override
    public Config getData() {
        return NoTextureRotations.config.getConfig();
    }

    @Override
    public void save() {
        NoTextureRotations.config.save();
    }
}
