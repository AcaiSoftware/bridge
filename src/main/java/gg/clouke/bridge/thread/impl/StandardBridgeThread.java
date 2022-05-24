package gg.clouke.bridge.thread.impl;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import gg.clouke.bridge.thread.BridgeThread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Clouke
 * @since 24.05.2022 17:38
 * © Bridge - All Rights Reserved
 */
public class StandardBridgeThread implements BridgeThread {

    private final Random random;
    private final List<ExecutorService> threads;

    public StandardBridgeThread(int size) {
        this.random = ThreadLocalRandom.current();
        this.threads = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            this.threads.add(Executors.newSingleThreadExecutor(new ThreadFactoryBuilder()
                    .setNameFormat("bridge-thread-" + i)
                    .setUncaughtExceptionHandler(new BridgeThreadException())
                    .build()));
        }
    }

    @Override
    public ExecutorService choose() {
        return get(random.nextInt(threads.size()));
    }

    @Override
    public ExecutorService get(int index) throws IndexOutOfBoundsException {
        return this.threads.get(index);
    }
}
