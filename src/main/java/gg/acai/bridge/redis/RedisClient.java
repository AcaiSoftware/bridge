package gg.acai.bridge.redis;

import com.google.common.collect.ImmutableList;
import gg.acai.bridge.Agent;
import gg.acai.bridge.Pong;
import gg.acai.bridge.provider.CompositeProprietor;
import lombok.Getter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Redis Client implementation of {@link RedisComponent}
 *
 * @author Clouke
 * @since 24.05.2022 17:10
 * © Bridge - All Rights Reserved
 */
@Getter
public class RedisClient implements RedisComponent {

    private final ReentrantLock casket;
    private final JedisPool pool;
    private final String password;
    private final List<Agent> agents;
    private final Pong response;
    private final boolean lock;
    private boolean active;

    protected RedisClient(String host, int port, String password, boolean lock, List<Agent> agents) {
        this.casket = new ReentrantLock();
        this.pool = new JedisPool(host, port);
        this.lock = lock;
        this.agents = agents;

        Jedis jedis = this.pool.getResource();
        jedis.auth(password);

        new Thread(() -> {
            for (Agent agent : agents) {
                JedisPubSub pubSub = new JedisPubSub() {
                    @Override
                    public void onMessage(String channel, String message) {
                        try {
                            agent.messenger().onMessage(message);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                jedis.subscribe(pubSub, agent.channel());
                agent.assign(pubSub);
            }
        }).start();

        this.response = new Pong();
        jedis.connect();
        try {
            long start = System.currentTimeMillis();
            response.message(jedis.ping());
            response.time(System.currentTimeMillis() - start);
        } catch (Exception e) {
            response.message(e.getMessage());
        }

        this.password = password;
        this.active = true;

        CompositeProprietor.register(this);
    }

    @Override
    public void write(String json, String channel) {
        try (Jedis redis = pool.getResource()) {
            if (lock) {
                casket.lock();
            }
            redis.auth(password);
            redis.publish(channel, json);
        } finally {
            if (lock) {
                casket.unlock();
            }
        }
    }

    @Override
    public ImmutableList<Agent> agents() {
        ImmutableList.Builder<Agent> builder = ImmutableList.builder();
        agents.forEach(builder::add);
        return builder.build();
    }

    @Override
    public Jedis pool() {
        try (Jedis jedis = pool.getResource()) {
            jedis.auth(this.password);
            return jedis;
        }
    }

    @Override
    public void close() {
        synchronized (this) {
            if (active) {
                agents.forEach(agent -> {
                    JedisPubSub sub = agent.pubSub();
                    if (sub != null) sub.unsubscribe();
                });
                pool.close();
                active = false;
            }
        }
    }

}
