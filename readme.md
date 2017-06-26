# Dlock

分布式锁的封装。有zk，redis等实现

## 说明

* zk 实现使用 curator
* redis 实现使用 redisson

## usage

```java

// zk config
ZkDLockConfig config = new ZkDLockConfig("127.0.0.1:2181,127.0.0.1:2182", "/lock");
lockFactory = new ZkDLockFactory(config);
lockFactory.init();


// redis config
RedisDLockConfig config = new RedisDLockConfig("127.0.0.1", 6379, 0, "dlock:");
lockFactory = new RedisDLockFactory(config);
lockFactory.init();


// use ReentrantLock
Lock lock = lockFactory.getReentrantLock("resouce");
lock.lock();
...
lock.unlock();


// use ReadWriteLock
ReadWriteLock readWriteLock =lockFactory.getReentrantReadWriteLock("resouce");
Lock lock = readWriteLock.readLock();
lock.lock();
...
lock.unlock();
```

