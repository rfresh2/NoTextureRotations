package com.ntr;

import com.ntr.config.*;
import com.ntr.mixin.client.AccessorMinecraftServer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Objects;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class NoTextureRotations implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("NoTextureRotations");
	public static final ConfigHandler config = getConfigHandler();
	public static final SecureRandom secureRandom = new SecureRandom();
	private static volatile KeyedHash masterKeyedHash = KeyedHash.loadOrCreate();
	private record LevelKeyedHash(KeyedHash master, Integer levelHashCode, KeyedHash keyedHash) {}
	private static volatile LevelKeyedHash levelKeyedHash = new LevelKeyedHash(masterKeyedHash, null, masterKeyedHash);
	public static final ThreadLocal<Boolean> inClientLevelTick = ThreadLocal.withInitial(() -> false);

	@Override
	public void onInitializeClient() {
		config.load();
		if (config.getConfig().mode == null) {
			// can happen if the file contained an unknown mode string for whatever reason
			config.getConfig().mode = Config.Mode.NO_ROTATIONS;
		}
		ClientTickEvents.START_WORLD_TICK.register(level -> inClientLevelTick.set(true));
		ClientTickEvents.END_WORLD_TICK.register(level -> inClientLevelTick.set(false));
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(literal("ntr:regenerateKey").executes(c -> {
				masterKeyedHash = KeyedHash.generate();
				Minecraft.getInstance().levelRenderer.allChanged();
				c.getSource().sendFeedback(Component.translatable("ntr.command.regenerateKey.success"));
				return 1;
			}));
			dispatcher.register(literal("ntr:toggleTextureRotations").executes(c -> {
				config.getConfig().disableTextureRotations = !config.getConfig().disableTextureRotations;
				Minecraft.getInstance().levelRenderer.allChanged();
				return 1;
			}));
			dispatcher.register(literal("ntr:toggleOffsets").executes(c -> {
				config.getConfig().disableOffsets = !config.getConfig().disableOffsets;
				Minecraft.getInstance().levelRenderer.allChanged();
				return 1;
			}));
			dispatcher.register(literal("ntr:toggle").executes(c -> {
				config.getConfig().disableTextureRotations = !config.getConfig().disableTextureRotations;
				config.getConfig().disableOffsets = !config.getConfig().disableOffsets;
				Minecraft.getInstance().levelRenderer.allChanged();
				return 1;
			}));
		});
	}

	public static KeyedHash keyedHash() {
		var mc = Minecraft.getInstance();
		ClientLevel level = null;
		if (mc != null) {
			level = mc.level;
		}
        Integer levelHashCode = null;
        if (level != null) {
            levelHashCode = level.hashCode();
        }
        var master = masterKeyedHash;
		var cached = levelKeyedHash;
		if (cached.master() == master && Objects.equals(cached.levelHashCode(), levelHashCode)) {
			return cached.keyedHash();
		}
        var keyedHash = master;
        if (level != null) {
			try {
				var levelContext = levelContext(mc, level);
				keyedHash = master.derive(levelContext);
				levelKeyedHash = new LevelKeyedHash(master, levelHashCode, keyedHash);
			} catch (Exception e) {
				LOGGER.warn("Failed computing level keyed hash");
			}
        }
		return keyedHash;
	}

	private static String levelContext(final Minecraft mc, final ClientLevel level) {
		var dimension = level.dimension().location().toString();
		var spServer = mc.getSingleplayerServer();
		if (spServer != null) {
			// save folder name, the display name can be changed
			var saveName = ((AccessorMinecraftServer) spServer).getStorageSource().getLevelDirectory().path().normalize().getFileName().toString();
			return "sp\0" + saveName + "\0" + dimension;
		}
		var serverData = mc.getCurrentServer();
		var address = serverData == null ? "" : serverData.ip.trim().toLowerCase(Locale.ROOT);
		return "mp\0" + address + "\0" + dimension;
	}

	private static ConfigHandler getConfigHandler() {
        return YACLConfigHelper.isYACLPresent()
			? new YACLConfigHandler()
			: new GSONConfigHandler();
	}
}
