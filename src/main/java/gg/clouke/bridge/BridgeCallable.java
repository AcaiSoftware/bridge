package gg.clouke.bridge;

import gg.clouke.bridge.relay.BridgeRequest;
import gg.clouke.bridge.relay.Packet;

/**
 * @author Clouke
 * @since 01.04.2022 18:59
 * © Bridge - All Rights Reserved
 */
public interface BridgeCallable {

    /**
     * Request packet to Bridge Provider
     * @param packet {@link Packet}
     * @param request {@link BridgeRequest}
     */
    public void request(Packet packet, BridgeRequest request);

    /**
     * Get the annotation information
     * @return Returns the {@link Identifier} from the subbed class
     */
    public default Identifier get() {
        return this.getClass().getAnnotation(Identifier.class);
    }

    /**
     * Get the packet from {@link Identifier}
     * @return Returns the direct {@link Packet} type
     */
    public default Packet type() {
        return this.get().type();
    }

}
