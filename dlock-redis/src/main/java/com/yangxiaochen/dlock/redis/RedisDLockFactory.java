package com.yangxiaochen.dlock.redis;

import com.yangxiaochen.dlock.DLockFactory;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * @author yangxiaochen
 * @date 2017/6/15 15:04
 */
public class RedisDLockFactory implements DLockFactory {

    private RedisDLockConfig config;
    private RedissonClient redissonClient;

    public RedisDLockFactory(RedisDLockConfig config) {
        this.config = config;
    }

    public RedisDLockFactory(RedissonClient redissonClient, String lockPrefix) {
        config = new RedisDLockConfig();
        config.setPrefix(lockPrefix);
        this.redissonClient = redissonClient;
    }

    @Override
    public void init() {
        if (redissonClient == null) {
            Config redissonConfig = new Config();
            redissonConfig.useSingleServer()
                    .setAddress("redis://" + config.getHost() + ":" + config.getPort())
                    .setDatabase(config.getDb());
            this.redissonClient = Redisson.create(redissonConfig);
        }

    }

    @Override
    public Lock getReentrantLock(String lockId) {
        return this.redissonClient.getLock(config.getPrefix() + lockId);

    }

    @Override
    public ReadWriteLock getReentrantReadWriteLock(String lockId) {
        return this.redissonClient.getReadWriteLock(config.getPrefix() + lockId);
    }

    @Override
    public void destory() {
        this.redissonClient.shutdown();
    }
}
