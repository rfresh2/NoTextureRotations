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
	public static final ConfigHandler config = getConfigHandler();
	public static final SecureRandom secureRandom = new SecureRandom();
	public static final long rotationsRandomOffset = secureRandom.nextLong();
	private static final int offsetsBound = 1000000;
	public static final int offsetsRandomOffsetX = secureRandom.nextInt(-offsetsBound, offsetsBound);
	public static final int offsetsRandomOffsetY = secureRandom.nextInt(-offsetsBound, offsetsBound);
	public static final int offsetsRandomOffsetZ = secureRandom.nextInt(-offsetsBound, offsetsBound);

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
