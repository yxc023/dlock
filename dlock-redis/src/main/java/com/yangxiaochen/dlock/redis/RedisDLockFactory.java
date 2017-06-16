package com.yangxiaochen.dlock.redis;

import com.yangxiaochen.dlock.DLockFactory;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * @author yangxiaochen
 * @date 2017/6/15 15:04
 */
public class RedisDLockFactory implements DLockFactory {
    @Override
    public void init() {

    }

    @Override
    public Lock getReentrantLock(String lockId) {
        return null;
    }

    @Override
    public ReadWriteLock getReentrantReadWriteLock(String lockId) {
        return null;
    }

    @Override
    public void destory() {

    }
}
