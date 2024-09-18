package com.ntr.config;

import net.minecraft.client.gui.screens.Screen;

public class SodiumHelper {
    private static boolean isSodiumPresent = false;
    private static boolean checked = false;
    private static boolean isSodium6Present = false;

    public static boolean isSodiumPresent() {
        if (checked) return isSodiumPresent;
        try {
            var clazz = Class.forName("me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI");
            isSodiumPresent = true;
        } catch (final Exception e) {
            // fall through
        }
        try {
            var clazz = Class.forName("net.caffeinemc.mods.sodium.client.gui.SodiumOptionsGUI");
            isSodiumPresent = true;
            isSodium6Present = true;
        } catch (final Exception e) {
            // fall through
        }
        checked = true;
        return isSodiumPresent;
    }

    public static boolean isSodium6Present() {
        if (!isSodiumPresent()) return false;
        return isSodium6Present;
    }

    public static Screen getSodiumOptionsScreen(final Screen parent) {
        if (!isSodiumPresent()) return null;
        if (isSodium6Present()) {
            return net.caffeinemc.mods.sodium.client.gui.SodiumOptionsGUI.createScreen(parent);
        } else {
            return me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI.createScreen(parent);
        }
    }
}
