package gg.acai.bridge.provider.thread;

import gg.acai.acava.io.Closeable;

import java.util.concurrent.ExecutorService;

/**
 * @author Clouke
 * @since 24.05.2022 17:37
 * © Bridge - All Rights Reserved
 */
public interface ThreadingProvider extends Closeable {

    /**
     * Returns a random thread from the pool
     *
     * @return Returns a random thread from the pool
     */
    ExecutorService choose();

    /**
     * Returns a thread from the pool at the given index
     *
     * @param index The index of the thread
     * @return Returns the thread at the given index
     * @throws IndexOutOfBoundsException If the index is out of bounds
     */
    ExecutorService get(int index) throws IndexOutOfBoundsException;

    /**
     * Enqueues the given stage to be executed on a thread
     *
     * @param stage The stage to enqueue
     * @return Returns a {@link Closeable} that can be used to close the stage
     */
    Closeable enqueue(AsyncStage stage);

}
