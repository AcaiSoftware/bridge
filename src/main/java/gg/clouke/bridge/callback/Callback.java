package gg.clouke.bridge.callback;

import gg.clouke.bridge.provider.BridgeRequest;

/**
 * @author Clouke
 * @since 24.05.2022 17:15
 * © Bridge - All Rights Reserved
 */
public interface Callback {

    void onCallback(BridgeRequest request, boolean success);

    boolean isAwaitingRequest();

}
