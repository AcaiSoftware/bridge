package gg.clouke.bridge.example;

import gg.clouke.bridge.Bridge;
import gg.clouke.bridge.redis.RedisConnector;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class ExamplePlugin extends JavaPlugin {

    @Getter(AccessLevel.PUBLIC)
    private static ExamplePlugin instance;

    private Bridge bridge;
    private RedisConnector redis;

    @Override
    public void onEnable() {
        instance = this;

        // Initializing bridge and redis
        this.redis = new RedisConnector();
        this.bridge = new Bridge();

        // Connecting redis
        this.redis.connect();

        // Registering listeners
        this.bridge.register(new ExamplePacket());
    }

    @Override
    public void onDisable() {
        instance = null;
    }
}
