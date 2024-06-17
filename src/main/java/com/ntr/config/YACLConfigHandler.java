package com.ntr.config;

import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;

public class YACLConfigHandler implements ConfigHandler {
    private final ConfigClassHandler<Config> HANDLER = ConfigClassHandler.<Config>createBuilder(Config.class)
        .id(ResourceLocation.fromNamespaceAndPath("ntr", "config"))
        .serializer(config -> GsonConfigSerializerBuilder.create(config)
            .setPath(configPath())
            .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
            .build())
        .build();

    @Override
    public Config getConfig() {
        return HANDLER.instance();
    }

    @Override
    public void save() {
        HANDLER.save();
    }

    @Override
    public void load() {
        HANDLER.load();
    }

    @Override
    public Screen createScreen(final Screen parent) {
        return HANDLER.generateGui().generateScreen(parent);
    }
}
