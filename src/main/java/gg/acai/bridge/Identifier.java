package gg.acai.bridge;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Packet Identifier, Required for {@link PacketListener} implementations
 *
 * @author Clouke
 * @since 01.04.2022 18:59
 * © Bridge - All Rights Reserved
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Identifier {

    /**
     * Gets the Packet Identifier
     *
     * @return {@link WrappedPacketIdentifier}
     */
    String value();

}

