package gg.clouke.bridge.redis.impl;

import gg.clouke.bridge.redis.RedisServer;
import redis.clients.jedis.JedisPubSub;

/**
 * @author Clouke
 * @since 24.05.2022 17:11
 * © Bridge - All Rights Reserved
 */
public class RedisBuilder implements IRedisBuilder {

    private String host;
    private int port;
    private String password;
    private String channel;
    private boolean lock;
    private JedisPubSub agent;

    @Override
    public RedisBuilder host(String host) {
        this.host = host;
        return this;
    }

    @Override
    public RedisBuilder port(int port) {
        this.port = port;
        return this;
    }

    @Override
    public RedisBuilder password(String password) {
        this.password = password;
        return this;
    }

    @Override
    public RedisBuilder channel(String channel) {
        this.channel = channel;
        return this;
    }

    @Override
    public RedisBuilder lock(boolean lock) {
        this.lock = lock;
        return this;
    }

    @Override
    public RedisBuilder agent(JedisPubSub agent) {
        this.agent = agent;
        return this;
    }

    @Override
    public RedisServer build() {
        return new RedisServer(host, port, password, channel, lock, agent);
    }
}
