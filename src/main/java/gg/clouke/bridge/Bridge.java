package gg.clouke.bridge;

import com.google.common.base.Preconditions;
import gg.clouke.bridge.command.BridgeCommand;
import gg.clouke.bridge.example.ExamplePlugin;
import gg.clouke.bridge.provider.BridgeRequest;
import gg.clouke.bridge.thread.BridgeThread;
import gg.clouke.bridge.thread.impl.StandardBridgeThread;
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

    private final ExamplePlugin plugin;
    private final List<BridgeCallable> packets;
    private final BridgeThread thread;

    public Bridge() {
        this.plugin = ExamplePlugin.getInstance();
        this.packets = new ArrayList<>();
        this.thread = new StandardBridgeThread(3); // Amount of threads to run in parallel (3)
    }

    /**
     * Register {@link BridgeCallable} to the Bridge Packet System
     * @param bridge {@link BridgeCallable}
     */
    public void register(BridgeCallable bridge) {
        Preconditions.checkState(!this.packets.contains(bridge), "BridgeCallable already registered");
        this.packets.add(bridge);
    }

    /**
     * Call each {@link BridgeCallable} packet class with the same packet identifier
     * @param packet {@link Packet}
     * @param request {@link BridgeRequest}
     */
    public void call(Packet packet, BridgeRequest request) {
        Preconditions.checkNotNull(request, "Request cannot be null");
        this.packets.stream()
                .filter(p -> p.type().equals(packet))
                .forEach(handler -> handler.onRequest(packet, request));
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
     */
    public <E> void execute(BridgeCommand<E> command) {
        try (Jedis jedis = this.plugin.getRedis().getPool()) {
            command.execute(jedis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}