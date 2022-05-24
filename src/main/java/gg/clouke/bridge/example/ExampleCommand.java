package gg.clouke.bridge.example;

/**
 * @author Clouke
 * @since 01.04.2022 19:23
 * © Bridge - All Rights Reserved
 */
public class ExampleCommand {

    /**
     * The redis command to execute
     * @param key The key
     * @param field The field
     * @param value The value
     */
    public void cache(String key, String field, String value) {
        ExamplePlugin.getInstance().getBridge().execute(bridge -> bridge.hset(key, field, value));
    }

}
