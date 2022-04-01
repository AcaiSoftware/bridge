package gg.clouke.bridge;

import gg.clouke.bridge.command.BridgeCommand;
import gg.clouke.bridge.example.ExamplePlugin;
import gg.clouke.bridge.relay.BridgeRequest;
import gg.clouke.bridge.relay.Packet;
import lombok.Getter;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Clouke
 * @since 01.04.2022 18:58
 * © Bridge - All Rights Reserved
 */
@Getter
public class Bridge {

    private final List<BridgeCallable> packets;
    private final ExamplePlugin plugin;

    public Bridge() {
        this.packets = new ArrayList<>();
        this.plugin = ExamplePlugin.getInstance();
    }

    /**
     * Register {@link BridgeCallable} to the Bridge Packet System
     * @param bridge {@link BridgeCallable}
     */
    public void register(BridgeCallable bridge) {
        this.packets.add(bridge);
    }

    /**
     * Call each {@link BridgeCallable} packet class with the same packet identifier
     * @param packet {@link Packet}
     * @param request {@link BridgeRequest}
     */
    public void call(Packet packet, BridgeRequest request) {
        this.packets.stream()
                .filter(p -> p.type().equals(packet))
                .forEach(handler -> handler.request(packet, request));
    }

    /**
     * Publish packet to Bridge
     * @param packet {@link Packet}
     */
    public void publish(String packet) {
        this.plugin.getRedis().write(packet);
    }

    /**
     * Run a Redis Command
     * @param command {@link BridgeCommand<E>}
     * @return Returns the Bridge result
     */
    public <E> E execute(BridgeCommand<E> command) {
        final Jedis jedis = this.plugin.getRedis().getJedisPool().getResource();

        return command.execute(jedis); // might implement callback
    }

}