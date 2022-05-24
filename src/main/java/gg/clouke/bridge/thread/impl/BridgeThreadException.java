package gg.clouke.bridge.thread.impl;

import gg.clouke.bridge.example.ExamplePlugin;

/**
 * @author Clouke
 * @since 24.05.2022 17:40
 * © Bridge - All Rights Reserved
 */
public class BridgeThreadException implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(final Thread thread, final Throwable throwable) {
        ExamplePlugin.getInstance().getLogger().severe(
                "Internal Bridge exception on thread " + thread.getName()
                + " ("
                + "priority: " + thread.getPriority() + " "
                + "status: " + thread.getState() + " "
                + "id: " + thread.getId() + " "
                + ") "
        );

        throwable.printStackTrace();
    }
}
