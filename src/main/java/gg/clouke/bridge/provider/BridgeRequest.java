package gg.clouke.bridge.provider;

import gg.clouke.bridge.Packet;
import gg.clouke.bridge.example.ExamplePlugin;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Clouke
 * @since 01.04.2022 19:02
 * © Bridge - All Rights Reserved
 */
public class BridgeRequest {

    @Getter
    private final Packet packet;
    private final Map<String, String> params;

    public BridgeRequest(Packet packet) {
        this.packet = packet;
        this.params = new HashMap<>();
    }

    /**
     * @param key String key
     * @param value String value
     */
    public BridgeRequest setParam(String key, String value) {
        this.params.put(key, value);
        return this;
    }

    /**
     * @param key {@link String} key
     * @return Returns the value query from key
     */
    public String getParam(String key) {
        if (containsParam(key)) {
            return this.params.get(key);
        }
        return null;
    }

    /**
     * @param key {@link String} key
     * @return Returns true if the key is in the parameters
     */
    public boolean containsParam(String key) {
        return this.params.containsKey(key);
    }

    /**
     * Remove the key from the parameters
     */
    public void removeParam(String key) {
        Optional.of(this.containsParam(key)).ifPresent(e -> this.params.remove(key));
    }

    /**
     * @return Returns the serialized request to Json
     */
    public String toJson() {
        return ExamplePlugin.getInstance().getGson().toJson(this);
    }

}
