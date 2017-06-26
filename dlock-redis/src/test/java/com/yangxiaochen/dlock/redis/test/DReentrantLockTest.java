package com.yangxiaochen.dlock.redis.test;

import com.yangxiaochen.dlock.DLockException;
import com.yangxiaochen.dlock.DLockFactory;
import com.yangxiaochen.dlock.redis.RedisDLockConfig;
import com.yangxiaochen.dlock.redis.RedisDLockFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    Logger logger = LogManager.getLogger(DReentrantLockTest.class);
    DLockFactory lockFactory;

    @Before
    public void before() throws Exception {

        RedisDLockConfig config = new RedisDLockConfig("127.0.0.1", 6379, 0, "dlock:");
        lockFactory = new RedisDLockFactory(config);
        lockFactory.init();
    }

    @After
    public void after() throws IOException {
        lockFactory.destory();
    }


    @Test(timeout = 25000)
    public void testLock() throws Exception {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(() -> {
                Lock lock = lockFactory.getReentrantLock("resouce");
                logger.info("lock");
                lock.lock();
                logger.info("lock ok");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.unlock();
                logger.info("unlock");
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
        new Thread(() -> {
            Lock lock = lockFactory.getReentrantLock("resouce");
            lock.lock();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock();
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
