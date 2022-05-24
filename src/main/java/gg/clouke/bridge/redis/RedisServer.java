package gg.clouke.bridge.redis;

import lombok.Getter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

/**
 * @author Clouke
 * @since 24.05.2022 17:10
 * © Bridge - All Rights Reserved
 */
@Getter
public class RedisServer implements RedisComponent {

    private final ReentrantLock casket;

    private final JedisPool pool;
    private final String password;
    private final String channel;
    private final JedisPubSub agent;
    private final boolean lock;

    private boolean active;

    public RedisServer(String host, int port, String password, String channel, boolean lock, JedisPubSub agent) {
        this.casket = new ReentrantLock();
        this.pool = new JedisPool(host, port);
        this.lock = lock;
        this.agent = agent;

        Jedis jedis = this.pool.getResource();
        jedis.auth(password);

        Optional.of(agent).ifPresent(agency -> (new Thread(() -> jedis.subscribe(agent, channel))).start());

        jedis.connect();

        this.channel = channel;
        this.password = password;

        this.active = true;
    }

    @Override
    public void write(String json) {
        Consumer<Jedis> redis = jedis -> {
            if (lock) {
                this.casket.lock();
            }

            jedis.auth(this.password);
            jedis.publish(this.channel, json);
        };

        Consumer<Jedis> consumer = redis.andThen(jedis -> {
            jedis.close();
            if (lock) {
                this.casket.unlock();
            }
        });

        consumer.accept(this.pool.getResource());
    }

    @Override
    public void dispose() {
        this.agent.unsubscribe();
        this.pool.destroy();
        this.active = false;
    }

    @Override
    public Jedis getPool() {
        try (final Jedis jedis = this.pool.getResource()) {
            jedis.auth(this.password);
            return jedis;
        }
    }

}
