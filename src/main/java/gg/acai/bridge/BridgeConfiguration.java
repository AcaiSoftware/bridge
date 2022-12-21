package gg.acai.bridge;

import com.google.gson.Gson;
import gg.acai.acava.annotated.Use;
import gg.acai.acava.io.logging.Color;
import gg.acai.acava.io.logging.Logger;
import gg.acai.acava.io.logging.StandardLogger;
import gg.acai.bridge.redis.RedisBuilder;
import gg.acai.bridge.redis.RedisClient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Clouke
 * @since 21.11.2022 15:59
 * © Bridge - All Rights Reserved
 */
@Use("Use BridgeContext#newBuilder() to create a new instance")
@Getter @RequiredArgsConstructor
public final class BridgeConfiguration {

    public static Builder newBuilder() {
        return new Builder();
    }

    private final Logger logger;
    private final Gson gson;
    private final int threads;
    private final int threadPriority;
    private final int maxConnections;
    private final RedisClient server;
    private final boolean profiling;
    private final boolean debug;
    private final boolean disableLogging;
    private final boolean upperCasePacketIdentifiers;

    public static class Builder {
        // default settings
        private Logger logger = new StandardLogger("Bridge", Color.CYAN);
        private Gson gson = new Gson();
        private int threads = 3;
        private int threadPriority = 5;
        private int maxConnections = 10;
        private RedisClient redisClient;
        private boolean profiling = true;
        private boolean debug = false;
        private boolean disableLogging = false;
        private boolean upperCasePacketIdentifiers = false;

        /**
         * Apply the logger to use for the Bridge API
         *
         * @param logger The logger to use
         */
        public Builder logger(Logger logger) {
            this.logger = logger;
            return this;
        }

        /**
         * Apply the Gson instance to use for the Bridge API
         *
         * @param gson The Gson instance to use
         */
        public Builder gson(Gson gson) {
            this.gson = gson;
            return this;
        }

        /**
         * Apply the amount of threads to use for the Bridge API
         *
         * @param threads The amount of threads to use
         */
        public Builder threads(int threads) {
            this.threads = threads;
            return this;
        }

        /**
         * Apply the thread priority to use for the Bridge API
         *
         * @param priority The thread priority to use
         */
        public Builder threadPriority(int priority) {
            this.threadPriority = Math.max(1, Math.min(10, priority));
            return this;
        }

        /**
         * Apply the maximum amount of connections to use for the Bridge API
         *
         * @param maxConnections The maximum amount of connections to use
         */
        public Builder maxConnections(int maxConnections) {
            this.maxConnections = maxConnections;
            return this;
        }

        /**
         * Apply the Redis Builder
         * Bridge will automatically build the Redis client
         *
         * @param builder The Redis Builder client to use
         */
        public Builder redisClient(RedisBuilder builder) {
            this.redisClient = builder.build();
            return this;
        }

        /**
         * Apply the Redis client to use for the Bridge API
         *
         * @param redisClient The Redis client to use
         */
        public Builder redisClient(RedisClient redisClient) {
            this.redisClient = redisClient;
            return this;
        }

        /**
         * Apply the profiling setting to use for the Bridge API
         *
         * @param profiling The profiling setting to use
         */
        public Builder profiler(boolean profiling) {
            this.profiling = profiling;
            return this;
        }

        /**
         * Apply the debug setting to use for the Bridge API
         *
         * @param debug The debug setting to use
         */
        public Builder debug(boolean debug) {
            this.debug = debug;
            return this;
        }

        /**
         * Apply to disable logging setting to use for the Bridge API
         */
        public Builder disableLogging() {
            this.disableLogging = true;
            return this;
        }

        /**
         * Apply the upper case packet identifiers setting to use for the Bridge API
         *
         * @param upperCasePacketIdentifiers The upper case packet identifiers setting to use
         */
        public Builder upperCasePacketIdentifiers(boolean upperCasePacketIdentifiers) {
            this.upperCasePacketIdentifiers = upperCasePacketIdentifiers;
            return this;
        }

        /**
         * Build the BridgeConfiguration instance
         *
         * @return Returns the BridgeConfiguration instance
         */
        public BridgeConfiguration build() {
            return new BridgeConfiguration(
                    logger,
                    gson,
                    threads,
                    threadPriority,
                    maxConnections,
                    redisClient,
                    profiling,
                    debug,
                    disableLogging,
                    upperCasePacketIdentifiers
            );
        }
    }

}
