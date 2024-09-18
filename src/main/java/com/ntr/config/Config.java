package com.ntr.config;

import dev.isxander.yacl.config.ConfigEntry;

public class Config {

    @ConfigEntry
    public boolean disableTextureRotations = true;

    @ConfigEntry
    public boolean disableOffsets = true;

    @ConfigEntry
    public Mode mode = Mode.NO_ROTATIONS;

    public enum Mode {
        NO_ROTATIONS("yacl3.config.enum.Mode.no_rotations"),
        SECURE_RANDOM("yacl3.config.enum.Mode.secure_random"),
        RANDOM_OFFSET("yacl3.config.enum.Mode.random_offset");

        private final String translateKey;

        Mode(final String translateKey) {
            this.translateKey = translateKey;
        }

        public int getId() {
            return ordinal();
        }

        public String getKey() {
            return translateKey;
        }
    }
}
