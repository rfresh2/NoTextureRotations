package com.ntr;

import com.ntr.config.ConfigHandler;
import com.ntr.config.GSONConfigHandler;
import com.ntr.config.YACLConfigHandler;
import com.ntr.config.YACLConfigHelper;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;

public class NoTextureRotations implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("NoTextureRotations");
	public static ConfigHandler config = getConfigHandler();
	public static SecureRandom secureRandom = new SecureRandom();

	@Override
	public void onInitializeClient() {
		config.load();
	}

	private static ConfigHandler getConfigHandler() {
        return YACLConfigHelper.isYACLPresent()
			? new YACLConfigHandler()
			: new GSONConfigHandler();
	}
}
