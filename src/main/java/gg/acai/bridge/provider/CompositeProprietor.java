package gg.acai.bridge.provider;

import gg.acai.acava.io.Closeable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A composite proprietor holding {@link Closeable}'s
 *
 * @author Clouke
 * @since 27.11.2022 14:52
 * © Bridge - All Rights Reserved
 */
public final class CompositeProprietor {

    private static final Lock lock;
    private static final List<Closeable> closeables;

    static {
        lock = new ReentrantLock();
        closeables = new ArrayList<>();
    }

    /**
     * Adds the given closeable to the composite proprietor
     *
     * @param closeable The closeable to add
     */
    public static void register(Closeable closeable) {
        closeables.add(closeable);
    }

    /**
     * Closes all the closeables in the composite proprietor
     */
    public static void close() {
        try {
            lock.lock();
            for (Closeable closeable : closeables) {
                if (closeable != null) {
                    closeable.close();
                }
            }
        } finally {
            lock.unlock();
        }
    }

}
