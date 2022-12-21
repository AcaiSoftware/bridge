package gg.acai.bridge;

import gg.acai.acava.annotated.Required;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Channel Annotation Identifier, Required for {@link PacketListener} implementations
 *
 * @author Clouke
 * @since 21.12.2022 13:35
 * © Acai Software - All Rights Reserved
 */
@Required
@Retention(RetentionPolicy.RUNTIME)
public @interface Channel {

    /**
     * Gets the channel identifier
     *
     * @return Returns the channel identifier
     */
    String value();

}
