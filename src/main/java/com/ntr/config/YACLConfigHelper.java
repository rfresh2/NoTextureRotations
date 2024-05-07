package com.ntr.config;

import java.util.concurrent.atomic.AtomicBoolean;

public class YACLConfigHelper {
    private static AtomicBoolean checkedYACL = new AtomicBoolean(false);
    private static AtomicBoolean isYACLPresent = new AtomicBoolean(false);

    public static synchronized boolean isYACLPresent() {
        if (checkedYACL.compareAndSet(false, true)) {
            try {
                Class.forName("dev.isxander.yacl3.config.v2.api.ConfigClassHandler");
                isYACLPresent.set(true);
                return true;
            } catch (final Throwable e) {
                isYACLPresent.set(false);
                return false;
            }
        }
        return isYACLPresent.get();
    }
}
