package com.zhengtoon.framework.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

/**
 * @auther: qindaorong
 * @Date: 2018/6/27 09:15
 * @Description:
 */
@Configuration
@ComponentScan({"com.zhengtoon.framework.components","com.zhengtoon.framework.aop"})
public class ZhengToonRedisLockConfiguration {
    private final Logger logger = LoggerFactory.getLogger(ZhengToonRedisLockConfiguration.class);

    RedissonClient redisson;

    @Value("${spring.redis.database}")
    private String database;

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

    @Value("${spring.redis.password}")
    private String password;

    @PostConstruct
    public void init(){
        logger.info("RedissonClient start init");
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + host + ":" + port);

        if(!StringUtils.isEmpty(password)){
            config.useSingleServer().setPassword(password);
        }
        redisson = Redisson.create(config);

        if(redisson == null ){
            logger.warn("RedissonClient is null, redis can't link ! redis lock can not used !");
        }else{
            logger.info("RedissonClient has created , redis lock can used !");
        }

        logger.info("RedissonClient end init");
    }


    @Bean
    public RedissonClient redissonClient(){
        return redisson;
    }

}
