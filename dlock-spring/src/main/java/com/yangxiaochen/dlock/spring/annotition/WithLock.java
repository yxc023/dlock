package com.yangxiaochen.dlock.spring.annotition;

import java.lang.annotation.*;

/**
 * @author yangxiaochen
 * @date 2017/12/1 01:38
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface WithLock {
    String id();
}
