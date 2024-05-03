package com.ntr;

import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.Boolean;
import dev.isxander.yacl3.config.v2.api.autogen.CustomDescription;
import dev.isxander.yacl3.config.v2.api.autogen.EnumCycler;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;

public class Config {
    public static ConfigClassHandler<Config> HANDLER = ConfigClassHandler.<Config>createBuilder(Config.class)
        .id(new ResourceLocation("ntr", "config"))
        .serializer(config -> GsonConfigSerializerBuilder.create(config)
            .setPath(FabricLoader.getInstance().getConfigDir().resolve("no-texture-rotations.json"))
            .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
            .build())
        .build();

    private static final String mainCategory = "Settings";

    @SerialEntry
    @AutoGen(category = mainCategory)
    @Boolean
    public boolean enabled = true;

    @SerialEntry
    @AutoGen(category = mainCategory)
    @Boolean
    @CustomDescription("Disables texture rotations based on their position")
    public boolean disableTextureRotations = true;

    @SerialEntry
    @AutoGen(category = mainCategory)
    @Boolean
    @CustomDescription("Disables blocks like flowers position being offset based on their position")
    public boolean disableOffsets = true;

    @SerialEntry
    @AutoGen(category = mainCategory)
    @EnumCycler
    @CustomDescription("Configures if rotations/offsets are fully cancelled or replaced with a secure random implementation.")
    public final Mode mode = Mode.NO_ROTATIONS;

    public enum Mode {
        NO_ROTATIONS, SECURE_RANDOM;
    }
}
