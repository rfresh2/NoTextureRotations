package com.ntr.config;

import com.google.gson.GsonBuilder;
import dev.isxander.yacl.api.ConfigCategory;
import dev.isxander.yacl.api.Option;
import dev.isxander.yacl.api.YetAnotherConfigLib;
import dev.isxander.yacl.config.GsonConfigInstance;
import dev.isxander.yacl.gui.controllers.BooleanController;
import dev.isxander.yacl.gui.controllers.cycling.EnumController;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class YACLConfigHandler implements ConfigHandler {
    private final GsonConfigInstance<Config> instance = new GsonConfigInstance<>(
        Config.class,
        configPath(),
        GsonBuilder::setPrettyPrinting
    );

    @Override
    public Config getConfig() {
        return instance.getConfig();
    }

    @Override
    public void save() {
        instance.save();
    }

    @Override
    public void load() {
        instance.load();
    }

    public Screen createScreen(final Screen parent) {
        return YetAnotherConfigLib.create(instance, (defaults, config, builder) -> builder
            .title(Component.literal("NoTextureRotations"))
            .category(ConfigCategory.createBuilder()
                          .name(Component.translatable("yacl3.config.ntr:config.category.Settings"))
                          .option(Option.createBuilder(Boolean.class)
                                      .name(Component.translatable("yacl3.config.ntr:config.disableTextureRotations"))
                                      .binding(true, () -> config.disableTextureRotations, v -> config.disableTextureRotations = v)
                                      .controller(BooleanController::new)
                                      .build())
                          .option(Option.createBuilder(Boolean.class)
                                      .name(Component.translatable("yacl3.config.ntr:config.disableOffsets"))
                                      .binding(true, () -> config.disableOffsets, v -> config.disableOffsets = v)
                                      .controller(BooleanController::new)
                                      .build())
                          .option(Option.createBuilder(Config.Mode.class)
                                      .name(Component.translatable("yacl3.config.ntr:config.mode"))
                                      .binding(Config.Mode.NO_ROTATIONS, () -> config.mode, v -> config.mode = v)
                                      .controller(option -> new EnumController<>(option, v -> Component.translatable(v.getKey())))
                                      .build())
                          .build())
        ).generateScreen(parent);
    }
}
