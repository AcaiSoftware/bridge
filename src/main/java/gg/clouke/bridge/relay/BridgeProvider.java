package gg.clouke.bridge.relay;

import com.google.gson.Gson;
import gg.clouke.bridge.example.ExamplePlugin;
import redis.clients.jedis.JedisPubSub;

/**
 * @author Clouke
 * @since 01.04.2022 19:01
 * © Bridge - All Rights Reserved
 */
public class BridgeProvider extends JedisPubSub {

    private final ExamplePlugin plugin = ExamplePlugin.getInstance();

    /**
     * <p>
     * Handles Bridge packet on {@link JedisPubSub} message
     *
     * @param channel Jedis Channel
     * @param message Obeying Bridge Packet
     */
    @Override
    public void onMessage(String channel, String message) {
        if (!channel.equals("Bridge"))
            return;

        final BridgeRequest request = new Gson().fromJson(message, BridgeRequest.class);
        final Packet packet = request.getPacket();

        this.plugin.getBridge().call(packet, request);
    }
}