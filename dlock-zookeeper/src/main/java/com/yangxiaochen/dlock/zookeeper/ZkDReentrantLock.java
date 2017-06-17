package com.yangxiaochen.dlock.zookeeper;

import com.yangxiaochen.dlock.DLockException;
import com.yangxiaochen.dlock.DReentranLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

/**
 * @author yangxiaochen
 * @date 2016/12/27 15:24
 */
public class ZkDReentrantLock implements DReentranLock {

    private static Logger logger = LogManager.getLogger();

    private InterProcessMutex mutex;
    private String lockName;

    ZkDReentrantLock(InterProcessMutex mutex, String lockName) {
        this.lockName = lockName;
        this.mutex = mutex;
    }

    @Override
    public void lock() {
        try {
            logger.trace("locking {}", Thread.currentThread().getName());
            mutex.acquire();
            logger.trace("lock {}", Thread.currentThread().getName());
        } catch (Exception e) {
            throw new DLockException(e);
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        try {
            logger.trace("locking {}", Thread.currentThread().getName());
            mutex.acquire();
            logger.trace("lock {}", Thread.currentThread().getName());
        } catch (Exception e) {
            if (e instanceof InterruptedException) {
                throw (InterruptedException) e;
            }
            throw new DLockException(e);
        }
    }

    @Override
    public boolean tryLock() {
        try {
            logger.trace("locking {}", Thread.currentThread().getName());
            boolean ret = mutex.acquire(0, TimeUnit.MILLISECONDS);
            logger.trace("lock {} {}", Thread.currentThread().getName(), ret);
            return ret;
        } catch (Exception e) {
            throw new DLockException(e);
        }
    }

    @Override
    public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
        try {
            logger.trace("locking {}", Thread.currentThread().getName());
            boolean ret = mutex.acquire(timeout, unit);
            logger.trace("lock {} {}", Thread.currentThread().getName(), ret);
            return ret;
        } catch (InterruptedException e) {
            throw e;
        } catch (Exception e) {
            throw new DLockException(e);
        }
    }

    @Override
    public void unlock() {
        try {
            mutex.release();
            logger.trace("unlock {}", Thread.currentThread().getName());
        } catch (Exception e) {
            throw new DLockException(e);
        }
    }

    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException();
    }

}
