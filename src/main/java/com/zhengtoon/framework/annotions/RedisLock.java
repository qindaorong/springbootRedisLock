package com.zhengtoon.framework.annotions;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisLock {

    //加锁key
    String lockPrefix() default "lockPrefix";

    //等待执行时间(默认0.05秒)
    long waitTime() default 50L;

    //方法执行超时时间（默认2秒）
    long timeout() default 2000L;
}
