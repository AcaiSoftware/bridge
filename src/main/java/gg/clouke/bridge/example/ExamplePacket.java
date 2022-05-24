package gg.clouke.bridge.example;

import gg.clouke.bridge.BridgeCallable;
import gg.clouke.bridge.Identifier;
import gg.clouke.bridge.provider.BridgeRequest;
import gg.clouke.bridge.Packet;

/**
 * @author Clouke
 * @since 01.04.2022 19:00
 * © Bridge - All Rights Reserved
 */

@Identifier(type = Packet.TEST_PACKET)
public class ExamplePacket implements BridgeCallable {

    /**
     * The method that is called when the packet is received.
     * @param packet {@link Packet}
     * @param request {@link BridgeRequest}
     */
    @Override
    public void onRequest(Packet packet, BridgeRequest request) {
        String message = request.getParam("MESSAGE");
        System.out.println("request=" + message);
    }
}
