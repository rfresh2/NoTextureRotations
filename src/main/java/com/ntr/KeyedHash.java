package com.ntr;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HexFormat;

import static java.nio.charset.StandardCharsets.UTF_8;

public final class KeyedHash {
    public static final Path KEY_PATH = FabricLoader.getInstance().getConfigDir().resolve("no-texture-rotations.key");
    private final HashFunction hashFunction;
    private final long k0;
    private final long k1;

    private KeyedHash(final long k0, final long k1) {
        this.hashFunction = Hashing.sipHash24(k0, k1);
        this.k0 = k0;
        this.k1 = k1;
    }

    public static KeyedHash loadOrCreate() {
        try {
            if (Files.exists(KEY_PATH)) {
                var bytes = HexFormat.of().parseHex(Files.readString(KEY_PATH).trim());
                if (bytes.length == 16) return fromBytes(bytes);
                NoTextureRotations.LOGGER.warn("Invalid key file, generating a new key");
            }
        } catch (final Throwable e) {
            NoTextureRotations.LOGGER.error("Failed to load key, generating a new key", e);
        }
        return generate();
    }

    // generates a new random key and overwrites the key file
    public static KeyedHash generate() {
        var bytes = new byte[16];
        NoTextureRotations.secureRandom.nextBytes(bytes);
        try {
            Files.writeString(KEY_PATH, HexFormat.of().formatHex(bytes));
        } catch (final Throwable e) {
            // still secure, just not stable across sessions
            NoTextureRotations.LOGGER.error("Failed to save key, using a temporary key", e);
        }
        return fromBytes(bytes);
    }

    private static KeyedHash fromBytes(final byte[] bytes) {
        var buf = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);
        return new KeyedHash(buf.getLong(), buf.getLong());
    }

    public KeyedHash derive(final String context) {
        return new KeyedHash(
            hashFunction.hashString("0\0" + context, UTF_8).asLong(),
            hashFunction.hashString("1\0" + context, UTF_8).asLong()
        );
    }

    public long hash(final long m) {
        return hashFunction.hashLong(m).asLong();
    }

    // SipHash-2-4 reimpl
    // inlined from guava's sipHash24
    // avoids excessive object allocation overhead
    // but i'm not 100% confident in correctness yet, needs testing
//    public long hash(final long m) {
//        long v0 = k0 ^ 0x736f6d6570736575L;
//        long v1 = k1 ^ 0x646f72616e646f6dL;
//        long v2 = k0 ^ 0x6c7967656e657261L;
//        long v3 = k1 ^ 0x7465646279746573L;
//
//        // message block
//        v3 ^= m;
//        for (int i = 0; i < 2; i++) {
//            v0 += v1; v1 = Long.rotateLeft(v1, 13); v1 ^= v0; v0 = Long.rotateLeft(v0, 32);
//            v2 += v3; v3 = Long.rotateLeft(v3, 16); v3 ^= v2;
//            v0 += v3; v3 = Long.rotateLeft(v3, 21); v3 ^= v0;
//            v2 += v1; v1 = Long.rotateLeft(v1, 17); v1 ^= v2; v2 = Long.rotateLeft(v2, 32);
//        }
//        v0 ^= m;
//
//        // final block: message length (8) in the top byte
//        final long b = 8L << 56;
//        v3 ^= b;
//        for (int i = 0; i < 2; i++) {
//            v0 += v1; v1 = Long.rotateLeft(v1, 13); v1 ^= v0; v0 = Long.rotateLeft(v0, 32);
//            v2 += v3; v3 = Long.rotateLeft(v3, 16); v3 ^= v2;
//            v0 += v3; v3 = Long.rotateLeft(v3, 21); v3 ^= v0;
//            v2 += v1; v1 = Long.rotateLeft(v1, 17); v1 ^= v2; v2 = Long.rotateLeft(v2, 32);
//        }
//        v0 ^= b;
//
//        v2 ^= 0xff;
//        for (int i = 0; i < 4; i++) {
//            v0 += v1; v1 = Long.rotateLeft(v1, 13); v1 ^= v0; v0 = Long.rotateLeft(v0, 32);
//            v2 += v3; v3 = Long.rotateLeft(v3, 16); v3 ^= v2;
//            v0 += v3; v3 = Long.rotateLeft(v3, 21); v3 ^= v0;
//            v2 += v1; v1 = Long.rotateLeft(v1, 17); v1 ^= v2; v2 = Long.rotateLeft(v2, 32);
//        }
//        return v0 ^ v1 ^ v2 ^ v3;
//    }
}
