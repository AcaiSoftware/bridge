package gg.acai.bridge;

import gg.acai.acava.cache.CacheDuplex;
import gg.acai.acava.cache.CacheLiteral;
import gg.acai.acava.io.Closeable;
import gg.acai.acava.scheduler.AsyncPlaceholder;
import gg.acai.acava.scheduler.Schedulers;
import gg.acai.bridge.provider.CompositeProprietor;

import java.util.Collection;

/**
 * RequestMapper - A cache mapper extension holding all the registered {@link PacketListener} implementations
 *
 * @author Clouke
 * @since 13.12.2022 19:44
 * © Acai Software - All Rights Reserved
 */
public class RequestMapper implements Closeable {

    private final CacheDuplex<String, PacketListener> cache;

    public RequestMapper() {
        this.cache = new CacheLiteral<>();
        CompositeProprietor.register(this);
    }

    /**
     * Gets the {@link PacketListener} implementation from the cache
     *
     * @param id The identifier of the packet listener
     * @return Returns the packet listener, if it exists, otherwise null
     */
    public PacketListener get(String id) {
        return cache.get(id);
    }

    /**
     * Registers the {@link PacketListener} to the cache
     *
     * @param listener The packet listener to register
     */
    public RequestMapper register(PacketListener listener) {
        cache.set(listener.type(), listener);
        return this;
    }

    /**
     * Registers a collection of Packet Listeners to the cache
     *
     * @param collection The collection of packet listeners to register
     */
    public RequestMapper register(Collection<? extends PacketListener> collection) {
        collection.forEach(this::register);
        return this;
    }

    /**
     * Registers a raw class extending Packet Listener, automatically instantiating it
     *
     * @param rawClass The raw class to register
     */
    public RequestMapper register(Class<? extends PacketListener> rawClass) {
        try {
            register(rawClass.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Registers the Packet Listener Asynchronously
     *
     * @param listener The packet listener to register
     * @return Returns a promise of the packet listener
     */
    public AsyncPlaceholder<RequestMapper> registerAsync(PacketListener listener) {
        return Schedulers.supplyAsync(() -> register(listener));
    }

    /**
     * Registers a collection of Packet Listeners Asynchronously
     *
     * @param collection The collection of packet listeners to register
     * @return Returns a promise of the packet listener
     */
    public AsyncPlaceholder<RequestMapper> registerAsync(Collection<? extends PacketListener> collection) {
        return Schedulers.supplyAsync(() -> register(collection));
    }

    /**
     * Registers a raw class extending Packet Listener Asynchronously, automatically instantiating it
     *
     * @param rawClass The raw class to register
     * @return Returns a promise of the packet listener
     */
    public AsyncPlaceholder<RequestMapper> registerAsync(Class<? extends PacketListener> rawClass) {
        return Schedulers.supplyAsync(() -> register(rawClass));
    }

    /**
     * Unregisters the {@link PacketListener} from the cache
     *
     * @param id The packet identifier to unregister
     */
    public RequestMapper unregister(String id) {
        cache.delete(id);
        return this;
    }

    /**
     * Unregisters the {@link PacketListener} from the cache asynchronously
     *
     * @param id The packet identifier to unregister
     * @return Returns a promise of the packet listener
     */
    public AsyncPlaceholder<RequestMapper> unregisterAsync(String id) {
        return Schedulers.supplyAsync(() -> unregister(id));
    }

    /**
     * Invalidates all null values from the cache
     *
     * @return Returns true if the cache is empty, otherwise false
     */
    public boolean invalidate() {
        cache.invalidateNulls();
        return cache.isEmpty();
    }

    /**
     * Clears the cache
     */
    @Override
    public void close() {
        cache.close();
    }
}
