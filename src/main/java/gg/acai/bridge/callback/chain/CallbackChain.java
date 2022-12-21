package gg.acai.bridge.callback.chain;

import gg.acai.bridge.callback.Callback;
import gg.acai.bridge.io.BridgePacket;

/**
 * @author Clouke
 * @since 24.05.2022 17:17
 * © Bridge - All Rights Reserved
 */
public interface CallbackChain {

    /**
     * @param callback {@link Callback} to be added to the listeners
     */
    void subscribe(Callback callback);

    /**
     * @param callback {@link Callback} to be removed from the listeners
     */
    void unsubscribe(Callback callback);

    /**
     * Unsubscribes all listeners
     */
    void unsubscribeAll();

    /**
     * Calls all subscribed listeners with the given {@link BridgePacket}
     *
     * @param packet {@link BridgePacket} to be passed to the listeners
     * @param success {@link Boolean} whether the request was successful
     */
    void fire(BridgePacket packet, boolean success);

    /**
     * Calls all subscribed listeners with the given {@link BridgePacket} and identifier
     *
     * @param identifier {@link String} identifier to be passed to the listeners
     * @param packet {@link BridgePacket} to be passed to the listeners
     * @param success {@link Boolean} whether the request was successful
     */
    void fire(String identifier, BridgePacket packet, boolean success);

}
