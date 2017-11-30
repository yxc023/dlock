package com.yangxiaochen.dlock.spring;

import com.yangxiaochen.dlock.DLockFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author yangxiaochen
 * @date 2017/12/1 01:48
 */
public class TestDlockFactory implements DLockFactory {

    Map<String, ReentrantLock> reentrantLockMap;
    Map<String, ReentrantReadWriteLock> reentrantReadWriteLockMap;

    @Override
    public void init() {
        reentrantLockMap = new HashMap<>();
        reentrantReadWriteLockMap = new HashMap<>();
    }

    @Override
    public Lock getReentrantLock(String lockId) {
        synchronized (reentrantLockMap) {
            if (reentrantLockMap.get(lockId) == null) {
                reentrantLockMap.put(lockId, new ReentrantLock());
            }
        }
        return reentrantLockMap.get(lockId);
    }

    @Override
    public ReadWriteLock getReentrantReadWriteLock(String lockId) {
        synchronized (reentrantReadWriteLockMap) {
            if(!reentrantReadWriteLockMap.containsKey(lockId)) {
                reentrantReadWriteLockMap.put(lockId, new ReentrantReadWriteLock());
            }
        }
        return reentrantReadWriteLockMap.get(lockId);
    }

    @Override
    public void destory() {

    }
}
