package gg.acai.bridge.callback;

import gg.acai.bridge.Bridge;
import gg.acai.bridge.Channel;
import gg.acai.bridge.Identifier;
import gg.acai.bridge.PacketListener;
import gg.acai.bridge.callback.chain.CallbackChain;
import gg.acai.bridge.io.BridgePacket;

/**
 * The standard callback listener that listens for packets with the packet identifier "bridge_std::callback"
 *
 * @author Clouke
 * @since 20.12.2022 20:07
 * © Acai Software - All Rights Reserved
 */
@Channel("bridge")
@Identifier("bridge_std::callback")
public final class CallbackReceivePacket implements PacketListener {

    @Override
    public void onIncomingPacket(BridgePacket packet) {
        boolean success = !packet.containsParameter("successful") || Boolean.parseBoolean(packet.getParameter("successful"));
        String identifier = packet.getParameter("identifier");
        CallbackChain chain = Bridge.getInstance().getCallbackChain();

        if (identifier == null) {
            chain.fire(packet, success);
            return;
        }

        chain.fire(identifier, packet, success);
    }
}
