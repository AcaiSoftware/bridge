package gg.acai.bridge.codec;

import gg.acai.acava.io.Callback;

import javax.annotation.Nonnull;

/**
 * @author Clouke
 * @since 08.12.2022 15:21
 * © Bridge - All Rights Reserved
 */
public interface Encoder<E, N> {

    /**
     * Encodes the {@link E} from {@param n}
     *
     * @param n The {@link N} to encode
     * @return Returns the encoded {@link E}
     */
    E encode(@Nonnull N n);

    /**
     * Encodes with a callback
     *
     * @param n The {@link N} to encode
     * @param callback The {@link Callback} to call after encoding
     */
    default void encode(@Nonnull N n, @Nonnull Callback<E> callback) {
        E e = encode(n);
        callback.onCallback(e);
    }

}
