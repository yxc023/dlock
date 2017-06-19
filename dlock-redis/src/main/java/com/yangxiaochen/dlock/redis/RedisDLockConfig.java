package com.yangxiaochen.dlock.redis;

/**
 * @author yangxiaochen
 * @date 2017/6/18 17:10
 */
public class RedisDLockConfig {
    private String host;
    private int port;
    private int db;
    private String prefix;

    public RedisDLockConfig() {
    }

    public RedisDLockConfig(String host, int port, int db, String prefix) {
        this.host = host;
        this.port = port;
        this.db = db;
        this.prefix = prefix;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getDb() {
        return db;
    }

    public void setDb(int db) {
        this.db = db;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
