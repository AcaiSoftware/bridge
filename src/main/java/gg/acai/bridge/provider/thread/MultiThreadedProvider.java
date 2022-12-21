package gg.acai.bridge.provider.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import gg.acai.acava.io.Closeable;
import gg.acai.bridge.Bridge;
import gg.acai.bridge.provider.CompositeProprietor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Clouke
 * @since 24.05.2022 17:38
 * © Bridge - All Rights Reserved
 */
public final class MultiThreadedProvider implements ThreadingProvider {

    private static final int PRIORITY = Bridge
            .config()
            .getThreadPriority();

    private final Random random;
    private final List<ExecutorService> threads;

    public MultiThreadedProvider(int size) {
        this.random = ThreadLocalRandom.current();
        this.threads = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            ThreadFactory thread = new ThreadFactoryBuilder()
                    .setNameFormat("Bridge Thread #" + i)
                    .setUncaughtExceptionHandler(new ThreadedException())
                    .setPriority(PRIORITY)
                    .build();

            ExecutorService exec = Executors.newSingleThreadExecutor(thread);
            this.threads.add(exec);
        }

        CompositeProprietor.register(this);
    }

    @Override
    public ExecutorService choose() {
        return get(random.nextInt(threads.size()));
    }

    @Override
    public ExecutorService get(int index) throws IndexOutOfBoundsException {
        return this.threads.get(index);
    }

    @Override
    public Closeable enqueue(AsyncStage stage) {
        runOnAvailableThread(stage.task());
        return stage;
    }

    @Override
    public void close() {
        synchronized (threads) {
            for (ExecutorService thread : threads) {
                thread.shutdown();
            }
        }
    }

    private void runOnAvailableThread(Runnable runnable) {
        choose().execute(runnable);
    }
}
