package com.ntr;

import com.ntr.config.YACLConfigHandler;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class ModMenuScreen implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (screen) -> {
            var handler = NoTextureRotations.config.getHandler();
            if (handler instanceof YACLConfigHandler yaclHandler) {
                return yaclHandler.createScreen(screen);
            }
            return null;
        };
    }
}
