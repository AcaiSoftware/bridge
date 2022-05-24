package gg.clouke.bridge.command;

import redis.clients.jedis.Jedis;

/**
 * @author Clouke
 * @since 01.04.2022 19:05
 * © Bridge - All Rights Reserved
 */
public interface BridgeCommand<E> {

    /**
     * <p>
     * Bridge Task enforcement
     * @param p {@link Jedis}
     */
    void execute(Jedis p);

}
