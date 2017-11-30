package com.yangxiaochen.dlock.spring;

import com.yangxiaochen.dlock.spring.annotition.WithLock;

/**
 * @author yangxiaochen
 * @date 2017/12/1 02:01
 */

public class FooService {

    @WithLock(id = "lock1")
    public void foo() {
        System.out.println("foo, " + Thread.currentThread().getName());
    }

    @WithLock(id = "lock:${0}:${1}")
    public void foo2(int a, Long b) {
        System.out.println("foo, " + Thread.currentThread().getName());
    }
}
