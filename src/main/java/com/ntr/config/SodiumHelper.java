package com.ntr.config;

import net.minecraft.client.gui.screens.Screen;

public class SodiumHelper {
    private static boolean isSodiumPresent = false;
    private static boolean checked = false;

    public static boolean isSodiumPresent() {
        if (checked) return isSodiumPresent;
        try {
            var clazz = Class.forName("net.caffeinemc.mods.sodium.client.SodiumClientMod");
            isSodiumPresent = true;
        } catch (final Exception e) {
            // fall through
        }
        checked = true;
        return isSodiumPresent;
    }

    public static Screen getSodiumOptionsScreen(final Screen parent) {
        if (!isSodiumPresent()) return null;
        return net.caffeinemc.mods.sodium.client.gui.VideoSettingsScreen.createScreen(parent);
    }
}
