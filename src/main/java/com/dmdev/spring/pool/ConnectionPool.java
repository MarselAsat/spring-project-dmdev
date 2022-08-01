package com.dmdev.spring.pool;

import java.util.List;
import java.util.Map;

public class ConnectionPool {
    private final String username;
    private final Integer poolSize;
    private final List<String> args;
    private final Map<String, Object> properties;

    public ConnectionPool(String username, Integer poolSize, List<String> args, Map<String, Object> properties) {
        this.username = username;
        this.poolSize = poolSize;
        this.args = args;
        this.properties = properties;
    }
}
