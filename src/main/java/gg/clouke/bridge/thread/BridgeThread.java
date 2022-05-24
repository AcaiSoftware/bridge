package gg.clouke.bridge.thread;

import java.util.concurrent.ExecutorService;

/**
 * @author Clouke
 * @since 24.05.2022 17:37
 * © Bridge - All Rights Reserved
 */
public interface BridgeThread {

    ExecutorService choose();

    ExecutorService get(int index) throws IndexOutOfBoundsException;

}
