package gg.acai.bridge.redis;

import gg.acai.bridge.Agent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Clouke
 * @since 24.05.2022 17:11
 * © Bridge - All Rights Reserved
 */
public final class RedisBuilder {

    private String host;
    private int port;
    private String password;
    private boolean lock;
    private final List<Agent> agents = new ArrayList<>();

    /**
     * Applies the given host to the builder
     *
     * @param host The host of the redis server, e.g. localhost
     */
    public RedisBuilder host(String host) {
        this.host = host;
        return this;
    }

    /**
     * Applies the given port to the builder
     *
     * @param port The port of the redis server, e.g. 6379
     */
    public RedisBuilder port(int port) {
        this.port = port;
        return this;
    }

    /**
     * Applies the given password to the builder
     *
     * @param password The password of the redis server, e.g. 1234
     */
    public RedisBuilder password(String password) {
        this.password = password;
        return this;
    }

    /**
     * Defines if the redis pub/sub handler should use locks while processing messages
     *
     * @param lock The lock of the redis server, e.g. true
     */
    public RedisBuilder lock(boolean lock) {
        this.lock = lock;
        return this;
    }

    public RedisBuilder addAgent(Agent agent) {
        this.agents.add(agent);
        return this;
    }

    /**
     * Builds a new {@link RedisClient} instance
     */
    public RedisClient build() {
        return new RedisClient(host, port, password, lock, agents);
    }
}
