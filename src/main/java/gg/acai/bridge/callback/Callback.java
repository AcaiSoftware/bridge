package gg.acai.bridge.callback;

import gg.acai.acava.annotated.RequiredAnnotation;
import gg.acai.bridge.io.BridgePacket;

/**
 * @author Clouke
 * @since 24.05.2022 17:15
 * © Bridge - All Rights Reserved
 */
@FunctionalInterface
@RequiredAnnotation(CallbackIdentifier.class)
public interface Callback {

    /**
     * Called when the callback is executed
     *
     * @param packet The request packet
     * @param success Whether the callback was successful
     */
    void onCallback(BridgePacket packet, boolean success);

    /**
     * Override this method to return whether the callback is awaiting a response
     *
     * @return Returns the response state
     */
    default boolean isAwaitingRequest() {
        return false;
    }

    /**
     * @return Returns true if the callback subbed has a {@link CallbackIdentifier} annotation
     */
    default boolean hasIdentifier() {
        return this.getClass().isAnnotationPresent(CallbackIdentifier.class);
    }

    /**
     * Gets the identifier of the callback
     *
     * @return Returns the identifier of the callback or null if it doesn't have one
     */
    default CallbackIdentifier getIdentifier() {
        Class<?> clazz = this.getClass();
        if (hasIdentifier()) {
            return clazz.getAnnotation(CallbackIdentifier.class);
        }
        return null;
    }

}
