package com.zhengtoon.framework.aop;

import com.zhengtoon.framework.annotions.RedisLock;
import com.zhengtoon.framework.components.RedisLocker;
import com.zhengtoon.framework.dto.RedisLockParam;
import com.zhengtoon.framework.exception.UnableToAquireLockException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class RedisLockAop {

    private static final Logger logger = LoggerFactory.getLogger(RedisLockAop.class);

    @Autowired
    RedisLocker distributedLocker;

    @Pointcut("@annotation(com.zhengtoon.framework.annotions.RedisLock)")
    private void redisLockAspect(){
    }

    @Around("redisLockAspect()")
    public Object lockAroundAction(ProceedingJoinPoint pjp){

        Object result = null;

        Boolean isGetLocked = Boolean.FALSE;

        //get @interface parameter
         RedisLockParam redisLockParam= loadCustomLabelParameter(pjp);

        logger.info("RedisLockParam =[lockPrefix:{},timeout:{},waitTime:{}]",redisLockParam.getLockPrefix(),redisLockParam.getTimeout(),redisLockParam.getWaitTime());

        //try get lock
        try {
            isGetLocked = distributedLocker.lock(redisLockParam.getLockPrefix(),redisLockParam.getWaitTime(),redisLockParam.getTimeout());
            logger.info("get redis lock~~~~");
        } catch (UnableToAquireLockException e) {
            e.printStackTrace();
            logger.warn("not get redis lock~~~~");
        }

        // get lock, resume method
        if(isGetLocked){
            // get lock, resume method
            try {
                result =pjp.proceed();
                logger.info("get redis lock, method has bean proceed  ");
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                logger.error("method proceed has error,error message is {}",throwable.getMessage());
            }
        }else{
            // not have lock , stop method
            logger.warn("do not have redis lock, method don't proceed");
        }
        return result;
    }


    private RedisLockParam loadCustomLabelParameter(ProceedingJoinPoint pjp){
        MethodSignature sign =  (MethodSignature)pjp.getSignature();
        Method method = sign.getMethod();
        RedisLock annotation = method.getAnnotation(RedisLock.class);
        return new RedisLockParam(annotation.lockPrefix(),annotation.timeout(),annotation.waitTime());
    }


}
