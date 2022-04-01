package gg.clouke.bridge.example;

import gg.clouke.bridge.Bridge;

/**
 * @author Clouke
 * @since 01.04.2022 19:11
 * © Bridge - All Rights Reserved
 */
public class ExampleRequest {

    /**
     * <p> Example request. </p>
     * Serialize an object to strings and send it to Bridge, which where you can deserialize it.
     * @param message The message to send over to Bridge
     */
    public void sendPacket(String message) {
        Bridge bridge = ExamplePlugin.getInstance().getBridge();

        bridge.publish(message);
    }

}
