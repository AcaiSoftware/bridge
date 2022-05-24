package gg.clouke.bridge.redis;

import gg.clouke.bridge.redis.impl.RedisBuilder;
import redis.clients.jedis.Jedis;

/**
 * @author Clouke
 * @since 24.05.2022 17:10
 * © Bridge - All Rights Reserved
 */
public interface RedisComponent {

    static RedisBuilder create() {
        return new RedisBuilder();
    }

    void write(String json);

    void dispose();

    Jedis getPool();

}
