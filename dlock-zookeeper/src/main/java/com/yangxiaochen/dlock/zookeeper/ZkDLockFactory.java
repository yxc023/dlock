package com.yangxiaochen.dlock.zookeeper;

import com.yangxiaochen.dlock.DLockFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.curator.retry.BoundedExponentialBackoffRetry;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * @author yangxiaochen
 * @date 2017/6/17 15:14
 */
public class ZkDLockFactory implements DLockFactory {

    private CuratorFramework client;
    private ZkDLockConfig config;

    public ZkDLockFactory(ZkDLockConfig config) {
        this.config = config;
    }

    @Override
    public void init() {
        client = CuratorFrameworkFactory.newClient(config.getConnectString(), new BoundedExponentialBackoffRetry(500, 1800000, 29));
        client.start();
    }

    @Override
    public Lock getReentrantLock(String lockId) {
        InterProcessMutex mutex = new InterProcessMutex(client, config.getLockBase() + "/" + lockId);
        return new ZkDReentrantLock(mutex, lockId);
    }

    @Override
    public ReadWriteLock getReentrantReadWriteLock(String lockId) {
        InterProcessReadWriteLock mutex = new InterProcessReadWriteLock(client, config.getLockBase() + "/" + lockId);
        ZkDReentrantLock readLock = new ZkDReentrantLock(mutex.readLock(), lockId);
        ZkDReentrantLock writeLock = new ZkDReentrantLock(mutex.writeLock(), lockId);
        return new ZkDReentrantReadWriteLock(readLock, writeLock);
    }

    @Override
    public void destory() {
        client.close();
    }

    private CuratorFramework getClient() {
        if (client == null) {
            throw new IllegalStateException("DistributedLockConfig not init");
        }
        return client;
    }

}
