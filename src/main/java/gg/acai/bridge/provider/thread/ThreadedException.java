package gg.acai.bridge.provider.thread;

import gg.acai.acava.io.logging.Logger;
import gg.acai.bridge.Bridge;

/**
 * @author Clouke
 * @since 24.05.2022 17:40
 * © Bridge - All Rights Reserved
 */
public class ThreadedException implements Thread.UncaughtExceptionHandler {

    private static final Logger LOGGER = Bridge
            .getInstance()
            .configuration()
            .getLogger();

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        final String error =
                "Internal Bridge exception on thread " + thread.getName() + " ("
                + "priority: " + thread.getPriority() + " "
                + "status: " + thread.getState() + " "
                + "id: " + thread.getId() + " "
                + ") ";

        LOGGER.log(error);
        throwable.printStackTrace();
    }
}
