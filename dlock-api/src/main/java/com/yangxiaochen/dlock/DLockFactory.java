package com.yangxiaochen.dlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * @author yangxiaochen
 * @date 2017/6/15 15:00
 */
public interface DLockFactory {
    void init();
    Lock getReentrantLock(String lockId);
    ReadWriteLock getReentrantReadWriteLock(String lockId);
    void destory();
}
