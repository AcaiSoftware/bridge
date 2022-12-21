package gg.acai.bridge.redis;

import com.google.common.collect.ImmutableList;
import gg.acai.acava.annotated.Use;
import gg.acai.acava.io.Closeable;
import gg.acai.bridge.Agent;
import redis.clients.jedis.Jedis;

import javax.annotation.Nullable;

/**
 * @author Clouke
 * @since 24.05.2022 17:10
 * © Bridge - All Rights Reserved
 */
@Use("Use RedisComponent#newBuilder() to create a new instance.")
public interface RedisComponent extends Closeable {

    /**
     * @return Returns a new {@link RedisBuilder} instance
     */
    static RedisBuilder newBuilder() {
        return new RedisBuilder();
    }

    /**
     * Write a message to the redis server
     *
     * @param json The json message to write
     * @param channel The target channel to write to
     */
    void write(String json, String channel);

    /**
     * Gets an immutable list of all agents
     *
     * @return Returns an immutable list of all agents
     */
    ImmutableList<Agent> agents();

    /**
     * @return Returns the {@link Jedis} pool
     */
    @Nullable
    Jedis pool();

}
