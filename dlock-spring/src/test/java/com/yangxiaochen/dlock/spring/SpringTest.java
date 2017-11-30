package com.yangxiaochen.dlock.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author yangxiaochen
 * @date 2017/12/1 01:44
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringConfig.class)
public class SpringTest {

    @Autowired
    FooService fooService;
    @Test
    public void test() throws Exception {
        fooService.foo();
        fooService.foo2(123,456L);
    }
}
