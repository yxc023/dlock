package com.yangxiaochen.dlock.spring;

import com.yangxiaochen.dlock.DLockFactory;
import com.yangxiaochen.dlock.spring.aop.LockAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author yangxiaochen
 * @date 2017/12/1 01:59
 */
@Configuration
@EnableAspectJAutoProxy
public class SpringConfig {

    @Bean(initMethod = "init")
    DLockFactory dLockFactory() {
        DLockFactory dLockFactory = new TestDlockFactory();
        return dLockFactory;
    }

    @Bean
    FooService fooService() {
        return new FooService();
    }

    @Bean
    LockAspect lockAspect() {
        return new LockAspect();
    }
}
