package gg.clouke.bridge.redis.impl;

import gg.clouke.bridge.redis.RedisServer;
import redis.clients.jedis.JedisPubSub;

/**
 * @author Clouke
 * @since 24.05.2022 17:10
 * © Bridge - All Rights Reserved
 */
public interface IRedisBuilder {

    RedisBuilder host(String host);

    RedisBuilder port(int port);

    RedisBuilder password(String password);

    RedisBuilder channel(String channel);

    RedisBuilder agent(JedisPubSub agent);

    RedisBuilder lock(boolean lock);

    RedisServer build();

}
