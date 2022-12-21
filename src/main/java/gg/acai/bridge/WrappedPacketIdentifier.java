package gg.acai.bridge;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * A wrapper class for holding a packet identifier
 *
 * @author Clouke
 * @since 21.11.2022 16:51
 * © Bridge - All Rights Reserved
 */
public final class WrappedPacketIdentifier {

    private static final boolean UPPER_CASE = Bridge
            .config()
            .isUpperCasePacketIdentifiers();

    private final String identifier;
    private final int DNA;

    public static WrappedPacketIdentifier createPacket(String identifier) {
        return new WrappedPacketIdentifier(identifier);
    }

    public WrappedPacketIdentifier(String identifier) {
        this.identifier = UPPER_CASE ? identifier.toUpperCase() : identifier.toLowerCase();
        this.DNA = hashCode();
    }

    public WrappedPacketIdentifier(Supplier<String> identifier) {
        this(identifier.get());
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public int getDNA() {
        return this.DNA;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof String) {
            return this.identifier.equals(o);
        } else if (o instanceof Integer) {
            return this.DNA == (int) o;
        }

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WrappedPacketIdentifier packet = (WrappedPacketIdentifier) o;
        return DNA == packet.DNA && identifier.equals(packet.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, DNA);
    }

}
