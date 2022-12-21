package gg.acai.bridge.callback;

import gg.acai.acava.annotated.Required;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * An annotation that is used to identify a callback
 *
 * @author Clouke
 * @since 12.12.2022 14:44
 * © Acai Software - All Rights Reserved
 */
@Required
@Retention(RetentionPolicy.RUNTIME)
public @interface CallbackIdentifier {

    /**
     * Gets the identifier of the callback class
     *
     * @return Returns the identifier of the callback class
     */
    String value();

}
