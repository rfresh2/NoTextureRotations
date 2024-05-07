package com.ntr.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.ntr.NoTextureRotations;
import com.ntr.config.Config;
import com.ntr.config.SodiumOptionStorage;
import me.jellysquid.mods.sodium.client.gui.SodiumGameOptionPages;
import me.jellysquid.mods.sodium.client.gui.options.OptionGroup;
import me.jellysquid.mods.sodium.client.gui.options.OptionImpl;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;
import me.jellysquid.mods.sodium.client.gui.options.binding.GenericBinding;
import me.jellysquid.mods.sodium.client.gui.options.control.CyclingControl;
import me.jellysquid.mods.sodium.client.gui.options.control.TickBoxControl;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Pseudo
@Mixin(value = SodiumGameOptionPages.class, remap = false)
public class MixinSodiumGameOptionPages {
    @Inject(method = "quality", at = @At(
        value = "INVOKE",
        target = "Lcom/google/common/collect/ImmutableList;copyOf(Ljava/util/Collection;)Lcom/google/common/collect/ImmutableList;"
    ))
    private static void injectNoTextureRotationsSettings(final CallbackInfoReturnable<OptionPage> cir,
                                                         @Local List<OptionGroup> groups) {
        try {
            groups.add(OptionGroup.createBuilder()
                           .add(OptionImpl.createBuilder(Boolean.TYPE, SodiumOptionStorage.INSTANCE)
                                    .setName(Component.translatable("yacl3.config.ntr:config.disableTextureRotations"))
                                    .setTooltip(Component.translatable("yacl3.config.ntr:config.disableTextureRotations.description"))
                                    .setControl(TickBoxControl::new)
                                    .setBinding(new GenericBinding<>(
                                        (config, value) -> config.disableTextureRotations = value,
                                        config -> config.disableTextureRotations))
                                    .build())
                           .add(OptionImpl.createBuilder(Boolean.TYPE, SodiumOptionStorage.INSTANCE)
                                    .setName(Component.translatable("yacl3.config.ntr:config.disableOffsets"))
                                    .setTooltip(Component.translatable("yacl3.config.ntr:config.disableOffsets.description"))
                                    .setControl(TickBoxControl::new)
                                    .setBinding(new GenericBinding<>(
                                        (config, value) -> config.disableOffsets = value,
                                        config -> config.disableOffsets))
                                    .build())
                           .add(OptionImpl.createBuilder(Config.Mode.class, SodiumOptionStorage.INSTANCE)
                                    .setName(Component.translatable("yacl3.config.ntr:config.mode"))
                                    .setTooltip(Component.translatable("yacl3.config.ntr:config.mode.description"))
                                    .setControl(o -> new CyclingControl<Config.Mode>(o, Config.Mode.class, new Component[]{
                                        Component.translatable("yacl3.config.enum.Mode.no_rotations"),
                                        Component.translatable("yacl3.config.enum.Mode.secure_random")
                                    }))
                                    .setBinding(new GenericBinding<>(
                                        (config, value) -> config.mode = value,
                                        config -> config.mode))
                                    .build())
                           .build());
        } catch (final Throwable e) {
            // no need to crash, these are completely optional
            NoTextureRotations.LOGGER.error("Failed injecting NoTextureRotations settings into Sodium", e);
        }
    }
}
