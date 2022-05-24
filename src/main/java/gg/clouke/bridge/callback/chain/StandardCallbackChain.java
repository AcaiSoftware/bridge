package gg.clouke.bridge.callback.chain;

import com.google.common.base.Preconditions;
import gg.clouke.bridge.callback.Callback;
import gg.clouke.bridge.provider.BridgeRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Clouke
 * @since 24.05.2022 17:16
 * © Bridge - All Rights Reserved
 */
public class StandardCallbackChain implements CallbackChain {

    private final List<Callback> listeners;

    public StandardCallbackChain() {
        this.listeners = new ArrayList<>();
    }

    @Override
    public void subscribe(Callback callback) {
        Preconditions.checkState(!this.listeners.contains(callback), "Callback already subscribed");
        this.listeners.add(callback);
    }

    @Override
    public void unsubscribe(Callback callback) {
        Preconditions.checkState(this.listeners.contains(callback), "Callback not subscribed");
        this.listeners.remove(callback);
    }

    @Override
    public void unsubscribeAll() {
        this.listeners.clear();
    }

    @Override
    public void fire(BridgeRequest request, boolean success) {
        Preconditions.checkNotNull(request, "Request cannot be null");
        this.listeners.stream()
                .filter(Callback::isAwaitingRequest)
                .forEach(subscriber -> subscriber.onCallback(request, success));
    }
}
