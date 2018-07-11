package com.zhengtoon.framework.interfaces;


import com.zhengtoon.framework.exception.UnableToAquireLockException;

/**
 * @auther: qindaorong
 * @Date: 2018/7/10 15:44
 * @Description:
 */
public interface DistributedLocker {

    /**
     * 获取锁
     * @param resourceName  锁的名称
     * @return
     * @throws UnableToAquireLockException
     */
    Boolean lock(String resourceName) throws UnableToAquireLockException;

    Boolean lock(String resourceName, Long waitTime, Long leaseTime) throws UnableToAquireLockException;
}
