package com.ntr;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.util.function.Supplier;

public class NoTextureRotations implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("NoTextureRotations");
	public static Supplier<Config> config = () -> Config.HANDLER.instance();
	public static SecureRandom secureRandom = new SecureRandom();

	@Override
	public void onInitializeClient() {
		Config.HANDLER.load();
	}
}
