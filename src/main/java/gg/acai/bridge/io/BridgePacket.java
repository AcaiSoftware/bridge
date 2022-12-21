package gg.acai.bridge.io;

import com.google.gson.Gson;
import gg.acai.acava.function.Action;
import gg.acai.bridge.Bridge;
import gg.acai.bridge.WrappedPacketIdentifier;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Clouke
 * @since 01.04.2022 19:02
 * © Bridge - All Rights Reserved
 */
public class BridgePacket {

    private static final Gson gson = Bridge
            .config()
            .getGson();

    private final WrappedPacketIdentifier packet;
    private final Map<String, String> parameters;
    private boolean written;

    public BridgePacket(WrappedPacketIdentifier packet) {
        this.packet = packet;
        this.parameters = new HashMap<>();
    }

    /**
     * Applies the given parameter to the packet, eg. "key" -> "value"
     *
     * @param key The key of the parameter
     * @param value The value of the parameter
     */
    public BridgePacket withRawParameter(String key, String value) {
        synchronized (parameters) {
            this.parameters.put(key, value);
            return this;
        }
    }

    /**
     * Serializes the value to a json string and adds it to the parameters
     *
     * @param key The key of the parameter
     * @param value The value of the parameter
     * @param type The type of the value
     */
    public BridgePacket withSerializableParameter(String key, Object value, Type type) {
        synchronized (parameters) {
            this.parameters.put(key, gson.toJson(value, type));
            return this;
        }
    }

    /**
     * Serializes the value to a json string and adds it to the parameters
     *
     * @param key The key of the parameter
     * @param value The value of the parameter
     * @param rawType The raw type of the value
     */
    public BridgePacket withSerializableParameter(String key, Object value, Class<?> rawType) {
        synchronized (parameters) {
            this.parameters.put(key, gson.toJson(value, rawType));
            return this;
        }
    }

    /**
     * Gets a raw parameter from the packet
     *
     * @param key {@link String} key of the parameter
     * @return Returns the value query from key
     */
    public String getParameter(String key) {
        if (containsParameter(key)) {
            return this.parameters.get(key);
        }
        return null;
    }

    /**
     * Functional action to get the value from the key if it exists
     *
     * @param key The key to query
     * @param action The action to perform if the key exists
     */
    public void ifPresent(String key, Action<String> action) {
        if (containsParameter(key)) {
            action.accept(this.parameters.get(key));
        }
    }

    /**
     * Gets a serializable object from the packet
     *
     * @param key The key to query
     * @param type The type of the object
     * @param <V> The value of the object to return
     * @return Returns an optional of the value
     */
    public <V> Optional<V> getSerializedParameter(String key, Type type) {
        if (containsParameter(key)) {
            try {
                return Optional.ofNullable(gson.fromJson(this.parameters.get(key), type));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

    /**
     * Checks if the packet contains the given key
     *
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

    /**
     * Gets the parameters as a {@link Map}
     *
     * @param clone Whether to clone the parameters
     * @return Returns the parameters as a {@link Map}
     */
    public Map<String, String> asMap(boolean clone) {
        return clone ? new HashMap<>(this.parameters) : this.parameters;
    }

    /**
     * Checks if the packet has been written to json
     *
     * @return Returns true if the packet has been written to json
     */
    public boolean isWritten() {
        return this.written;
    }

    public WrappedPacketIdentifier getWrappedPacket() {
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
