package com.ntr.config;

import com.google.gson.annotations.SerializedName;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.Boolean;
import dev.isxander.yacl3.config.v2.api.autogen.CustomDescription;
import dev.isxander.yacl3.config.v2.api.autogen.EnumCycler;

public class Config {
    private static final String mainCategory = "Settings";

    @SerialEntry
    @AutoGen(category = mainCategory)
    @Boolean
    @CustomDescription("yacl3.config.ntr:config.disableTextureRotations.description")
    public boolean disableTextureRotations = true;

    @SerialEntry
    @AutoGen(category = mainCategory)
    @Boolean
    @CustomDescription("yacl3.config.ntr:config.disableOffsets.description")
    public boolean disableOffsets = true;

    @SerialEntry
    @AutoGen(category = mainCategory)
    @EnumCycler
    @CustomDescription("yacl3.config.ntr:config.mode.description")
    public Mode mode = Mode.NO_ROTATIONS;

    public enum Mode {
        NO_ROTATIONS,
        @SerializedName(value = "KEYED_RANDOM", alternate = {"SECURE_RANDOM", "RANDOM_OFFSET", "REPEATING_SECTION"})
        KEYED_RANDOM
    }
}
