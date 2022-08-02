package com.dmdev.spring.pool;

import org.springframework.beans.factory.InitializingBean;

import java.util.List;
import java.util.Map;

public class ConnectionPool implements InitializingBean {
    private final String username;
    private final Integer poolSize;
    private final List<String> args;
    private Map<String, Object> properties;

    public ConnectionPool(String username, Integer poolSize, List<String> args, Map<String, Object> properties) {
        this.username = username;
        this.poolSize = poolSize;
        this.args = args;
        this.properties = properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    private void init(){
        System.out.println("Initialization connection pool");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Initialization with interface");
    }

    public void destroy(){
        System.out.println("Destroy connection pool");
    }
}
