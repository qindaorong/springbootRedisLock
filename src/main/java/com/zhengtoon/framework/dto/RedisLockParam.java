package com.zhengtoon.framework.dto;

import java.io.Serializable;

/**
 * @auther: qindaorong
 * @Date: 2018/7/10 16:47
 * @Description:
 */
public class RedisLockParam implements Serializable {

    private static final long serialVersionUID = 3769008544726786664L;

    private String lockPrefix;

    private Long timeout;

    private Long waitTime;

    public RedisLockParam(String lockPrefix, Long timeout, Long waitTime) {
        this.lockPrefix = lockPrefix;
        this.timeout = timeout;
        this.waitTime = waitTime;
    }

    public String getLockPrefix() {
        return lockPrefix;
    }

    public void setLockPrefix(String lockPrefix) {
        this.lockPrefix = lockPrefix;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    public Long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(Long waitTime) {
        this.waitTime = waitTime;
    }
}
