package gg.clouke.bridge.example;

import gg.clouke.bridge.Bridge;
import gg.clouke.bridge.provider.BridgeRequest;
import gg.clouke.bridge.Packet;

/**
 * @author Clouke
 * @since 01.04.2022 19:11
 * © Bridge - All Rights Reserved
 */
public class ExampleRequest {

    /**
     * <p> Example request. </p>
     * Serialize an object to strings and send it to Bridge, which where you can deserialize it.
     */
    public void sendPacket() {
        Bridge bridge = ExamplePlugin.getInstance().getBridge();

        String packet = new BridgeRequest(Packet.TEST_PACKET)
                .setParam("MESSAGE", "Hello World!")
                .toJson();

        bridge.publish(packet);
    }

}
