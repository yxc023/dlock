package com.yangxiaochen.dlock.zookeeper;

import com.yangxiaochen.dlock.DReentranReadWriteLock;

import java.util.concurrent.locks.Lock;

/**
 * @author yangxiaochen
 * @date 2016/12/28 15:36
 */
public class ZkDReentrantReadWriteLock implements DReentranReadWriteLock {
    private ZkDReentrantLock readLock;
    private ZkDReentrantLock writeLock;

    public ZkDReentrantReadWriteLock(ZkDReentrantLock readLock, ZkDReentrantLock writeLock) {
        this.readLock = readLock;
        this.writeLock = writeLock;
    }

    @Override
    public Lock writeLock() {
        return writeLock;
    }

    @Override
    public Lock readLock() {
        return readLock;
    }
}
