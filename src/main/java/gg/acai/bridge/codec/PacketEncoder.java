package gg.acai.bridge.codec;

import com.google.gson.Gson;
import gg.acai.bridge.Bridge;
import gg.acai.bridge.io.BridgePacket;

import javax.annotation.Nonnull;

/**
 * A packet encoder that encodes {@link String} (json) to {@link BridgePacket}
 *
 * @author Clouke
 * @since 08.12.2022 15:23
 * © Bridge - All Rights Reserved
 */
public class PacketEncoder implements Encoder<BridgePacket, String> {

    private static final Gson gson = Bridge
            .config()
            .getGson();

    @Override
    public BridgePacket encode(@Nonnull String json) {
        return gson.fromJson(json, BridgePacket.class);
    }

}
