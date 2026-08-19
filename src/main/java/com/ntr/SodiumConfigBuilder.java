package com.ntr;

import com.ntr.config.Config;
import net.caffeinemc.mods.sodium.api.config.ConfigEntryPoint;
import net.caffeinemc.mods.sodium.api.config.option.OptionFlag;
import net.caffeinemc.mods.sodium.api.config.structure.ConfigBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class SodiumConfigBuilder implements ConfigEntryPoint {
    @Override
    public void registerConfigLate(final ConfigBuilder builder) {
        builder.registerModOptions("notexturerotations")
//            .setIcon(Identifier.parse("notexturerotations:icon.png"))
            .addPage(builder.createOptionPage()
                .setName(Component.literal("NoTextureRotations"))
                .addOptionGroup(builder.createOptionGroup()
                    .setName(Component.literal("NoTextureRotations"))
                    .addOption(builder.createBooleanOption(ResourceLocation.parse("notexturerotations:disable_texture_rotations"))
                        .setName(Component.translatable("yacl3.config.ntr:config.disableTextureRotations"))
                        .setTooltip(Component.translatable("yacl3.config.ntr:config.disableTextureRotations.description"))
                        .setStorageHandler(NoTextureRotations.config::save)
                        .setBinding((v) -> NoTextureRotations.config.getConfig().disableTextureRotations = v, () -> NoTextureRotations.config.getConfig().disableTextureRotations)
                        .setDefaultValue(true)
                        .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD))
                    .addOption(builder.createBooleanOption(ResourceLocation.parse("notexturerotations:disable_offsets"))
                        .setName(Component.translatable("yacl3.config.ntr:config.disableOffsets"))
                        .setTooltip(Component.translatable("yacl3.config.ntr:config.disableOffsets.description"))
                        .setStorageHandler(NoTextureRotations.config::save)
                        .setBinding((v) -> NoTextureRotations.config.getConfig().disableOffsets = v, () -> NoTextureRotations.config.getConfig().disableOffsets)
                        .setDefaultValue(true)
                        .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD))
                    .addOption(builder.createEnumOption(ResourceLocation.parse("notexturerotations:mode"), Config.Mode.class)
                        .setName(Component.translatable("yacl3.config.ntr:config.mode"))
                        .setTooltip(Component.translatable("yacl3.config.ntr:config.mode.description"))
                        .setElementNameProvider((mode -> switch (mode) {
                            case NO_ROTATIONS -> Component.translatable("yacl3.config.enum.Mode.no_rotations");
                            case SECURE_RANDOM -> Component.translatable("yacl3.config.enum.Mode.secure_random");
                            case RANDOM_OFFSET -> Component.translatable("yacl3.config.enum.Mode.random_offset");
                        }))
                        .setStorageHandler(NoTextureRotations.config::save)
                        .setBinding((v) -> NoTextureRotations.config.getConfig().mode = v, () -> NoTextureRotations.config.getConfig().mode)
                        .setDefaultValue(Config.Mode.NO_ROTATIONS)
                        .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD))
                ));
    }
}
