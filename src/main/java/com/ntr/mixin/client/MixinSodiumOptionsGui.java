package com.ntr.mixin.client;

import com.google.common.collect.ImmutableList;
import com.ntr.config.Config;
import com.ntr.config.SodiumOptionStorage;
import me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI;
import me.jellysquid.mods.sodium.client.gui.options.OptionGroup;
import me.jellysquid.mods.sodium.client.gui.options.OptionImpl;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;
import me.jellysquid.mods.sodium.client.gui.options.binding.GenericBinding;
import me.jellysquid.mods.sodium.client.gui.options.control.CyclingControl;
import me.jellysquid.mods.sodium.client.gui.options.control.TickBoxControl;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Pseudo
@Mixin(value = SodiumOptionsGUI.class, remap = false)
public class MixinSodiumOptionsGui {

    @Final @Shadow private List<OptionPage> pages;

    @Inject(method = "<init>", at = @At(
        value = "RETURN"
    ))
    public void injectNoTextureRotationsSettings(final Screen prevScreen, final CallbackInfo ci) {
        pages.add(new OptionPage(Component.literal("NoTextureRotations"), ImmutableList.<OptionGroup>of(
            OptionGroup.createBuilder()
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
                .build())
        ));
    }
}
