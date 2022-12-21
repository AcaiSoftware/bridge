package gg.acai.bridge;

/**
 * Simple Ping/Pong extension of pinging the redis server
 *
 * @author Clouke
 * @since 21.12.2022 13:16
 * © Acai Software - All Rights Reserved
 */
public final class Pong {

    /**
     * Represents the pong received from the redis server in milliseconds
     */
    private long time;

    /**
     * Represents the response result from the redis server
     */
    private String message;

    public Pong(long time, String message) {
        this.time = time;
        this.message = message;
    }

    public Pong() {
        this(0L, "");
    }

    /**
     * Gets the pong time
     *
     * @return Returns the pong time
     */
    public long time() {
        return time;
    }

    /**
     * Gets the pong message
     *
     * @return Returns the pong message
     */
    public String message() {
        return message;
    }

    /**
     * Sets the pong time
     *
     * @param time The pong time
     */
    public Pong time(long time) {
        this.time = time;
        return this;
    }

    /**
     * Sets the pong message
     *
     * @param message The pong message
     */
    public Pong message(String message) {
        this.message = message;
        return this;
    }

}
