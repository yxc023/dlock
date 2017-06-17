package com.yangxiaochen.dlock.zookeeper;

/**
 * @author yangxiaochen
 * @date 2017/6/17 15:22
 */
public class ZkDLockConfig {

    private String lockBase;
    private String connectString;


    public ZkDLockConfig(String connectString, String lockBase) {
        this.lockBase = lockBase;
        this.connectString = connectString;
    }


    public String getLockBase() {
        return lockBase;
    }

    public void setLockBase(String lockBase) {
        this.lockBase = lockBase;
    }

    public String getConnectString() {
        return connectString;
    }

    public void setConnectString(String connectString) {
        this.connectString = connectString;
    }
}
