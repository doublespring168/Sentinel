package com.alibaba.csp.sentinel.dashboard.doublespring;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "zookeeper.config")
public class ZookeeperConfigProperties {
    private String connectString;
    private int sessionTimeout;
    private int connectionTimeout;

    public String getConnectString() {
        return connectString;
    }

    public ZookeeperConfigProperties setConnectString(String connectString) {
        this.connectString = connectString;
        return this;
    }

    public int getSessionTimeout() {
        return sessionTimeout;
    }

    public ZookeeperConfigProperties setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
        return this;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public ZookeeperConfigProperties setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
        return this;
    }
}
