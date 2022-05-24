package gg.clouke.bridge.example;

import com.google.gson.Gson;
import gg.clouke.bridge.Bridge;
import gg.clouke.bridge.callback.chain.CallbackChain;
import gg.clouke.bridge.callback.chain.StandardCallbackChain;
import gg.clouke.bridge.provider.BridgeProvider;
import gg.clouke.bridge.redis.RedisComponent;
import gg.clouke.bridge.redis.RedisServer;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class ExamplePlugin extends JavaPlugin {

    @Getter(AccessLevel.PUBLIC)
    private static ExamplePlugin instance;

    private Bridge bridge;
    private CallbackChain callback;
    private RedisServer redis;
    private Gson gson;

    @Override
    public void onEnable() {
        instance = this;

        // Register & connect redis
        this.redis = RedisComponent.create()
                .host("localhost")
                .port(6379)
                .password("")
                .channel("Bridge")
                .lock(true)
                .agent(new BridgeProvider())
                .build();

        // Register bridge
        this.bridge = new Bridge();

        this.gson = new Gson();
        this.callback = new StandardCallbackChain();


        // Registering listeners
        this.bridge.register(new ExamplePacket());
    }

    @Override
    public void onDisable() {
        this.redis.dispose();
    }
}
