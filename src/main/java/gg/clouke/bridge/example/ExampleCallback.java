package gg.clouke.bridge.example;

import gg.clouke.bridge.callback.Callback;
import gg.clouke.bridge.provider.BridgeRequest;

/**
 * @author Clouke
 * @since 24.05.2022 17:22
 * © Bridge - All Rights Reserved
 */
public class ExampleCallback implements Callback {

    private boolean awaiting;

    public ExampleCallback() {
        ExamplePlugin.getInstance().getCallback().subscribe(this);
    }

    @Override
    public void onCallback(BridgeRequest request, boolean success) {
        if (!awaiting)
            return;

        awaiting = false;
        System.out.println("Callback received, Packet: " + request.getPacket().name() + " Success: " + success);
    }

    @Override
    public boolean isAwaitingRequest() {
        return awaiting;
    }
}
