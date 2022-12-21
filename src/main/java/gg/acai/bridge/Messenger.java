package gg.acai.bridge;

/**
 * Messenger interface for sending and receiving packets, bound to {@link Agent}
 *
 * @author Clouke
 * @since 20.12.2022 22:21
 * © Acai Software - All Rights Reserved
 */
@FunctionalInterface
public interface Messenger {

    /**
     * Called when a message is received from the {@link Agent}
     * Provided by Redis
     *
     * @param message The message received
     */
    void onMessage(String message);

}
