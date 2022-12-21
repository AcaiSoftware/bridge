package gg.acai.bridge.provider.thread;

import gg.acai.acava.io.Closeable;

/**
 * A stage that is executed asynchronously holding a {@link Runnable} task
 *
 * @author Clouke
 * @since 21.12.2022 00:34
 * © Acai Software - All Rights Reserved
 */
public class AsyncStage implements Closeable {

    private Runnable task;

    public AsyncStage(Runnable task) {
        this.task = task;
    }

    public AsyncStage() {
        this(null);
    }

    /**
     * Assigns the given task to the stage
     *
     * @param runnable The task to assign
     */
    public AsyncStage claim(Runnable runnable) {
        this.task = runnable;
        return this;
    }

    /**
     * Gets the {@link Runnable} task
     *
     * @return The task
     */
    public Runnable task() {
        return this.task;
    }

    @Override
    public void close() {
        this.task = null;
    }

}
