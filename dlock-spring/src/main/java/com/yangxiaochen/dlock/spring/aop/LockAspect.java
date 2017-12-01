package com.yangxiaochen.dlock.spring.aop;

import com.yangxiaochen.dlock.DLockFactory;
import com.yangxiaochen.dlock.spring.annotition.WithLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.concurrent.locks.Lock;

/**
 * @author yangxiaochen
 * @date 2017/12/1 01:55
 */
@Aspect
public class LockAspect {
    @Autowired
    private DLockFactory dLockFactory;

    @Around("@annotation(withLock)")
    public Object around(ProceedingJoinPoint pjp, WithLock withLock) throws Throwable {
        String id = withLock.id();
        for (int i = 0; i < pjp.getArgs().length; i++) {
            Object arg = pjp.getArgs()[i];
            id = StringUtils.replace(id, "${" + i + "}", arg == null ? "" : arg.toString());
        }
        Object retVal;
        Lock lock = dLockFactory.getReentrantLock(id);
        try {
            lock.lock();
            retVal = pjp.proceed();
        } finally {
            lock.unlock();
        }
        return retVal;
    }
}
