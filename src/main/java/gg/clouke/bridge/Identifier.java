package gg.clouke.bridge;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Clouke
 * @since 01.04.2022 18:59
 * © Bridge - All Rights Reserved
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface Identifier {

    /**
     * Packet Identifier
     * @return {@link Packet}
     */
    Packet type();

}

