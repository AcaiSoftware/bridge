package gg.acai.bridge.io;

import com.google.gson.Gson;
import gg.acai.bridge.provider.BridgeProvider;
import gg.acai.bridge.Packet;
import gg.acai.bridge.util.Action;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Clouke
 * @since 01.04.2022 19:02
 * © Bridge - All Rights Reserved
 */
public class BridgeRequest {

    private static final Gson gson = BridgeProvider
            .getInstance()
            .getContext()
            .getGson();

    private final Packet packet;
    private final Map<String, String> parameters;
    private boolean written;

    public BridgeRequest(Packet packet) {
        this.packet = packet;
        this.parameters = new HashMap<>();
    }

    /**
     * @param key String key
     * @param value String value
     */
    public BridgeRequest withRawParameter(String key, String value) {
        synchronized (parameters) {
            this.parameters.put(key, value);
            return this;
        }
    }

    public BridgeRequest withSerializableParameter(String key, Object value, Type type) {
        synchronized (parameters) {
            this.parameters.put(key, gson.toJson(value, type));
            return this;
        }
    }

    public BridgeRequest withSerializableParameter(String key, Object value, Class<?> rawType) {
        synchronized (parameters) {
            this.parameters.put(key, gson.toJson(value, rawType));
            return this;
        }
    }

    /**
     * @param key {@link String} key
     * @return Returns the value query from key
     */
    public String getParameter(String key) {
        if (containsParameter(key)) {
            return this.parameters.get(key);
        }
        return null;
    }

    public void ifPresent(String key, Action<String> action) {
        if (containsParameter(key)) {
            action.accept(this.parameters.get(key));
        }
    }

    public <V> Optional<V> getSerializedParameter(String key, Type type) {
        if (containsParameter(key)) {
            return Optional.ofNullable(gson.fromJson(this.parameters.get(key), type));
        }
        return Optional.empty();
    }

    /**
     * @param key {@link String} key
     * @return Returns true if the key is in the parameters
     */
    public boolean containsParameter(String key) {
        synchronized (parameters) {
            return this.parameters.containsKey(key);
        }
    }

    /**
     * Remove the key from the parameters
     */
    public void removeParameter(String key) {
        synchronized (parameters) {
            this.parameters.remove(key);
        }
    }

    public Map<String, String> asMap(boolean clone) {
        return clone ? new HashMap<>(this.parameters) : this.parameters;
    }

    public boolean isWritten() {
        return this.written;
    }

    public Packet getPacket() {
        return this.packet;
    }

    /**
     * @return Returns the serialized request to Json
     */
    public String toJson() {
        this.written = true;
        return gson.toJson(this);
    }

    @Override
    public String toString() {
        return "BridgeRequest{" +
                "packet=" + packet +
                ", parameters=" + parameters +
                ", written=" + written +
                '}';
    }
}
