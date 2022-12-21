package gg.acai.bridge.io;

import gg.acai.bridge.WrappedPacketIdentifier;

/**
 * @author Clouke
 * @since 22.11.2022 16:58
 * © Bridge - All Rights Reserved
 */
public final class Packets {

    private Packets() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Creates a new {@link BridgePacket} with a packet identifier
     *
     * @param packet The packet identifier
     * @return The created packet
     */
    public static BridgePacket createPacket(WrappedPacketIdentifier packet) {
        return new BridgePacket(packet);
    }

    /**
     * Creates a new {@link BridgePacket} with a packet identifier as string
     *
     * @param packet The packet identifier as string
     * @return The created packet
     */
    public static BridgePacket createPacket(String packet) {
        return createPacket(new WrappedPacketIdentifier(packet));
    }

    /**
     * Creates a new {@link BridgePacket} aimed directly at callback identifiers
     *
     * @return The created packet
     */
    public static BridgePacket createCallbackPacket() {
        return createPacket("bridge_std::callback");
    }

}
