package com.ntr;

import com.google.gson.GsonBuilder;
import dev.isxander.yacl.api.ConfigCategory;
import dev.isxander.yacl.api.Option;
import dev.isxander.yacl.api.YetAnotherConfigLib;
import dev.isxander.yacl.config.ConfigEntry;
import dev.isxander.yacl.config.GsonConfigInstance;
import dev.isxander.yacl.gui.controllers.BooleanController;
import dev.isxander.yacl.gui.controllers.cycling.EnumController;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.OptionEnum;

public class Config {
    public static GsonConfigInstance<Config> configInstance = new GsonConfigInstance<>(
        Config.class,
        FabricLoader.getInstance().getConfigDir().resolve("no-texture-rotations.json"),
        GsonBuilder::setPrettyPrinting
    );

    public static Screen getConfigScreen(Screen parentScreen) {
        return YetAnotherConfigLib.create(configInstance, (defaults, config, builder) -> builder
            .title(Component.literal("NoTextureRotations"))
            .category(ConfigCategory.createBuilder()
                          .name(Component.translatable("yacl3.config.ntr:config.category.Settings"))
                          .option(Option.createBuilder(Boolean.class)
                                      .name(Component.translatable("yacl3.config.ntr:config.enabled"))
                                      .binding(true, () -> config.enabled, v -> config.enabled = v)
                                      .controller(BooleanController::new)
                                      .build())
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
                          .option(Option.createBuilder(Mode.class)
                                      .name(Component.translatable("yacl3.config.ntr:config.mode"))
                                      .binding(Mode.NO_ROTATIONS, () -> config.mode, v -> config.mode = v)
                                      .controller(EnumController::new)
                                      .build())
                          .build())
            ).generateScreen(parentScreen);
    }

    @ConfigEntry
    public boolean enabled = true;

    @ConfigEntry
    public boolean disableTextureRotations = true;

    @ConfigEntry
    public boolean disableOffsets = true;

    @ConfigEntry
    public Mode mode = Mode.NO_ROTATIONS;

    public enum Mode implements OptionEnum {
        NO_ROTATIONS("yacl3.config.enum.Mode.no_rotations"), SECURE_RANDOM("yacl3.config.enum.Mode.secure_random");

        private final String translateKey;

        Mode(final String translateKey) {
            this.translateKey = translateKey;
        }

        @Override
        public int getId() {
            return ordinal();
        }

        @Override
        public String getKey() {
            return translateKey;
        }
    }
}
