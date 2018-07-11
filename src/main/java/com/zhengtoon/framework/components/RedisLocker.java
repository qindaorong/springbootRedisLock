package com.zhengtoon.framework.components;

import com.zhengtoon.framework.exception.UnableToAquireLockException;
import com.zhengtoon.framework.interfaces.DistributedLocker;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @auther: qindaorong
 * @Date: 2018/7/10 15:46
 * @Description:
 */
@Component
public class RedisLocker implements DistributedLocker {

    private final static Long DEFULT_WAIT_TIME = 100L;

    private final static Long DEFULT_LEASE_TIME = 100L;

    @Autowired
    RedissonClient redissonClient;

    @Override
    public Boolean lock(String resourceName) throws UnableToAquireLockException{
        return lock(resourceName,DEFULT_WAIT_TIME,DEFULT_LEASE_TIME);
    }

    @Override
    public Boolean lock(String resourceName,Long waitTime, Long leaseTime) throws UnableToAquireLockException{
        RLock lock = redissonClient.getLock(resourceName);

        // Wait for 100 seconds seconds and automatically unlock it after lockTime seconds
        try {
            boolean success = lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
            if(success){
                return Boolean.TRUE;
            }else{
                return Boolean.FALSE;
            }
        }catch (InterruptedException e){
            throw new UnableToAquireLockException();
        }finally {
            lock.unlock();
        }
    }
}
