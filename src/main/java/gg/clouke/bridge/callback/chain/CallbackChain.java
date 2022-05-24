package gg.clouke.bridge.callback.chain;

import gg.clouke.bridge.callback.Callback;
import gg.clouke.bridge.provider.BridgeRequest;

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
     * Calls all subscribed listeners with the given {@link BridgeRequest}
     * @param request {@link BridgeRequest} to be passed to the listeners
     * @param success {@link Boolean} whether the request was successful
     */
    void fire(BridgeRequest request, boolean success);

}
