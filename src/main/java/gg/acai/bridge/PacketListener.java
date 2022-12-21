package gg.acai.bridge;

import gg.acai.bridge.io.BridgePacket;

/**
 * @author Clouke
 * @since 01.04.2022 18:59
 * © Bridge - All Rights Reserved
 */
@FunctionalInterface
public interface PacketListener {

    /**
     * Called by the agent's {@link Messenger} when a packet is received
     *
     * @param packet The packet received including its parameters
     */
    void onIncomingPacket(BridgePacket packet);

    /**
     * Gets the annotation information
     *
     * @return Returns the {@link Identifier} from the subbed class
     */
    default Identifier getIdentifier() {
        return this.getClass().getAnnotation(Identifier.class);
    }

    /**
     * Gets the channel identifier
     *
     * @return Returns the {@link Channel} from the subbed class
     */
    default Channel getChannel() {
        return this.getClass().getAnnotation(Channel.class);
    }

    /**
     * Gets the packet from {@link Identifier}
     *
     * @return Returns the direct {@link WrappedPacketIdentifier} type
     */
    default String type() {
        Identifier identifier = getIdentifier();
        return identifier != null ? identifier.value() : getClass()
                .getSimpleName()
                .toLowerCase();
    }

    /**
     * Gets the channel from {@link Channel}
     *
     * @return Returns the direct channel identifier, if the identifier is null it will return "bridge"
     */
    default String channel() {
        Channel channel = getChannel();
        return channel != null ? channel.value() : "bridge";
    }

}
