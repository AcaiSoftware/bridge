package gg.acai.bridge;

import gg.acai.acava.io.Closeable;
import gg.acai.bridge.callback.CallbackReceivePacket;
import gg.acai.bridge.callback.chain.CallbackChain;
import gg.acai.bridge.callback.chain.StandardCallbackChain;
import gg.acai.bridge.provider.CompositeProprietor;
import gg.acai.bridge.provider.thread.MultiThreadedProvider;
import gg.acai.bridge.provider.thread.ThreadingProvider;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Bridge is the primary extension for the Bridge API, which is a householder for all the
 * functionality within packets and thread management.
 *
 * @author Clouke
 * @since 01.04.2022 18:58
 * © Bridge - All Rights Reserved
 */
@Getter
public final class Bridge implements Closeable {

    private static Bridge instance;

    public static Bridge getInstance() {
        return instance;
    }

    public static BridgeConfiguration config() {
        return instance.config;
    }

    private final BridgeConfiguration config;
    private final Map<String, PacketListener> packets;
    private final ThreadingProvider threads;
    private final RequestMapper registry;
    private final CallbackChain callbackChain;

    public Bridge(BridgeConfiguration config) {
        instance = this;

        this.config = config;
        this.threads = new MultiThreadedProvider(config.getThreads());
        this.packets = new HashMap<>();
        this.callbackChain = new StandardCallbackChain();
        this.registry = new RequestMapper().register(new CallbackReceivePacket());
    }

    /**
     * Publish a packet to the server message queue
     *
     * @param packet The packet to publish
     */
    public void publish(String channel, String packet) {
        config.getServer().write(packet, channel);
    }

    public void publish(String json) {
        publish("bridge", json);
    }

    /**
     * Gets the Bridge thread pool
     *
     * @return Returns the Bridge thread pool
     */
    public ThreadingProvider pool() {
        return this.threads;
    }

    /**
     * Gets the Listeners registered as a Map
     *
     * @return Returns the Listeners registered as a Map
     */
    public Map<String, PacketListener> asMap() {
        return this.packets;
    }

    /**
     * Gets the configuration of Bridge
     *
     * @return Returns the configuration of Bridge
     */
    public BridgeConfiguration configuration() {
        return this.config;
    }

    /**
     * Closes Bridge & all of its resources
     * Disconnects from the redis server
     */
    @Override
    public void close() {
        CompositeProprietor.close();
    }

}