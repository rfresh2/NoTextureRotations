package com.ntr;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.ntr.config.*;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;

public class NoTextureRotations implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("NoTextureRotations");
	public static final ConfigHandler config = getConfigHandler();
	public static final SecureRandom secureRandom = new SecureRandom();
	public static final int randomOffsetBound = 1_000_000;
	public static final LoadingCache<Long, Integer> randomOffsetByChunkCache = CacheBuilder.newBuilder()
		.maximumSize(1000)
		.build(CacheLoader.from(NoTextureRotations::generateStableRandomOffset));

	public static int generateStableRandomOffset() {
		return secureRandom.nextInt(-randomOffsetBound, randomOffsetBound);
	}

	@Override
	public void onInitializeClient() {
		config.load();
		if (config.getConfig().mode == null) {
			// can happen if the file contained an unknown mode string for whatever reason
			config.getConfig().mode = Config.Mode.NO_ROTATIONS;
		}
	}

	private static ConfigHandler getConfigHandler() {
        return YACLConfigHelper.isYACLPresent()
			? new YACLConfigHandler()
			: new GSONConfigHandler();
	}
}
