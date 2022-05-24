package gg.clouke.bridge.provider;

import gg.clouke.bridge.Bridge;
import gg.clouke.bridge.Packet;
import gg.clouke.bridge.example.ExamplePlugin;
import redis.clients.jedis.JedisPubSub;

/**
 * @author Clouke
 * @since 01.04.2022 19:01
 * © Bridge - All Rights Reserved
 */
public class BridgeProvider extends JedisPubSub {

    private static final ExamplePlugin plugin = ExamplePlugin.getInstance();

    @Override
    public void onMessage(String channel, String message) {
        if (!channel.equals(plugin.getRedis().getChannel()))
            return;

        BridgeRequest request = plugin.getGson().fromJson(message, BridgeRequest.class);
        Packet packet = request.getPacket();
        Bridge bridge = plugin.getBridge();

        bridge.getThread().choose().execute(() -> bridge.call(packet, request));
    }
}