package gg.acai.bridge;

import gg.acai.acava.io.Closeable;
import gg.acai.bridge.codec.Encoder;
import gg.acai.bridge.codec.PacketEncoder;
import gg.acai.bridge.io.BridgePacket;
import gg.acai.bridge.provider.thread.AsyncStage;

/**
 * Standard implementation of {@link Messenger} extension for encoding {@link BridgePacket} objects
 * and publishing them to the server message queue.
 *
 * @author Clouke
 * @since 14.12.2022 18:02
 * © Acai Software - All Rights Reserved
 */
public final class BridgeMessenger implements Messenger {

    private static final Encoder<BridgePacket, String> ENCODER = new PacketEncoder();

    @Override
    public void onMessage(String message) {
        final Bridge bridge = Bridge.getInstance();
        ENCODER.encode(message, (packet) -> {
            Closeable task = bridge.pool()
                    .enqueue(new AsyncStage(() -> {
                        String id = packet.getWrappedPacket().getIdentifier();
                        bridge.getRegistry().get(id).onIncomingPacket(packet);
                    }));

            task.close();
        });
    }
}
