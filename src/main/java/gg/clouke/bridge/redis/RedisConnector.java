package gg.clouke.bridge.redis;

import gg.clouke.bridge.example.ExamplePlugin;
import gg.clouke.bridge.relay.BridgeProvider;
import lombok.Getter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author Clouke
 * @since 01.04.2022 19:06
 * © Bridge - All Rights Reserved
 */

@Getter
public class RedisConnector {

    private final ExamplePlugin plugin;
    private final BridgeProvider provider;
    private final String channel;

    private JedisPool jedisPool;

    private final String pool;
    private final int port;
    private final String password;

    @Getter
    private boolean active = false;

    public RedisConnector() {
        this.plugin = ExamplePlugin.getInstance();
        this.channel = "Bridge";
        this.provider = new BridgeProvider();
        this.pool = "host";
        this.port = 6379;
        this.password = "password";
    }

    /**
     * Connect to Redis
     */
    public void connect() {
        try {
            this.jedisPool = new JedisPool(pool, port);

            Jedis jedis = this.jedisPool.getResource();
            jedis.auth(password);
            (new Thread(() -> jedis.subscribe(provider, channel))).start();
            jedis.connect();

            this.active = true;
            this.plugin.getLogger().info("Successfully connect to Redis.");
        } catch (Exception e) {
            this.plugin.getLogger().warning("Error while connecting to Redis: " + e.getMessage());
            this.active = false;
        }
    }

    /**
     * @see JedisPool
     * <p> Disconnect from Redis
     */
    public void disconnect() {
        this.provider.unsubscribe();
        this.jedisPool.getResource().close();
        this.jedisPool.destroy();
        this.plugin.getLogger().info("Disconnected from Redis.");
    }

    /**
     * @param json Parse string to json, writes to redis
     */
    public void write(String json){
        try (final Jedis jedis = this.jedisPool.getResource()) {
            jedis.auth(password);
            jedis.publish(channel, json);
        }
    }
}


