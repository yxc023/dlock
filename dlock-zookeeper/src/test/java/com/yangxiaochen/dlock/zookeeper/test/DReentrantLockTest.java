package com.yangxiaochen.dlock.zookeeper.test;

import com.yangxiaochen.dlock.DLockException;
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
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @author yangxiaochen
 * @date 2016/12/27 16:53
 */
public class DReentrantLockTest {
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


    @Test(timeout = 25000)
    public void testLock() throws Exception {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    Lock lock = lockFactory.getReentrantLock("resouce");
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

    @Test
    public void testTryLock() throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Lock lock = lockFactory.getReentrantLock("resouce");
                lock.lock();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.unlock();
            }
        }, "LockThread").start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Lock lock = lockFactory.getReentrantLock("resouce");
        assert !lock.tryLock(); // lock false
        assert lock.tryLock(4, TimeUnit.SECONDS); // this will lockok
        lock.unlock();
    }


    @Test(expected = DLockException.class)
    public void unLock() {
        Lock lock = lockFactory.getReentrantLock("resouce");
        lock.unlock();
    }


}
