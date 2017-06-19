package com.yangxiaochen.dlock.zookeeper.test;

import com.yangxiaochen.dlock.DLockFactory;
import com.yangxiaochen.dlock.zookeeper.ZkDLockConfig;
import com.yangxiaochen.dlock.zookeeper.ZkDLockFactory;
import org.apache.curator.test.TestingCluster;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * @author yangxiaochen
 * @date 2016/12/28 15:45
 */
public class DReentrantReadWriteLockTest {

    TestingCluster cluster;
    String connectString;
//    DistributedLockConfig config;
    DLockFactory lockFactory;

    @Before
    public void before() throws Exception {
        cluster = new TestingCluster(3);
        cluster.start();
        Thread.sleep(2000);
        connectString = cluster.getConnectString();
        ZkDLockConfig config = new ZkDLockConfig(connectString, "/lock");
        lockFactory = new ZkDLockFactory(config);
        lockFactory.init();
    }

    @After
    public void after() throws IOException {
        lockFactory.destory();
        cluster.close();
    }

    @Test(timeout = 4000)
    public void readLockTest() throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    ReadWriteLock readWriteLock =lockFactory.getReentrantReadWriteLock("resouce");
                    Lock lock = readWriteLock.readLock();
                    lock.lock();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lock.unlock();
                }
            }, "T" + i));
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
    }

    @Test(timeout = 8000)
    public void writeLockTest() throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            final int k = i;
            threads.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    ReadWriteLock readWriteLock = lockFactory.getReentrantReadWriteLock("resouce");
                    Lock lock = (k == 5) ?readWriteLock.writeLock():readWriteLock.readLock();
                    lock.lock();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lock.unlock();
                }
            }, "T-" + i));
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();

        }
    }
}
