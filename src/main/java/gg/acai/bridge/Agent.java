package gg.acai.bridge;

import redis.clients.jedis.JedisPubSub;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

/**
 * An agent holds a channel and a messenger, which is used to send and receive packets
 *
 * @author Clouke
 * @since 21.12.2022 13:38
 * © Acai Software - All Rights Reserved
 */
public final class Agent {

    private final String channel;
    private final Messenger messenger;
    private JedisPubSub pubSub;
    private boolean assigned;

    public Agent(String channel, Messenger messenger) {
        this.channel = Objects.requireNonNull(channel, "channel cannot be null");
        this.messenger = Objects.requireNonNull(messenger, "messenger cannot be null");
    }

    public Agent(String channel) {
        this(channel, new BridgeMessenger());
    }

    /**
     * Gets the channel identifier
     *
     * @return Returns the channel identifier
     */
    @Nonnull
    public String channel() {
        return this.channel;
    }

    /**
     * Gets the messenger of the agent
     *
     * @return Returns the messenger of the agent
     */
    @Nonnull
    public Messenger messenger() {
        return this.messenger;
    }

    /**
     * Gets the pubsub instance of the agent
     *
     * @return Returns the pubsub instance of the agent, or null if not assigned
     */
    @Nullable
    public JedisPubSub pubSub() {
        return this.pubSub;
    }

    /**
     * Assigns the pubsub instance of the agent
     *
     * @param pubSub The pubsub instance of the agent
     * @return Returns true if the pubsub instance was assigned, false if already assigned
     */
    public boolean assign(JedisPubSub pubSub) {
        boolean assigned = this.assigned;
        if (!assigned) {
            this.pubSub = pubSub;
            this.assigned = true;
        }
        return assigned;
    }

}
