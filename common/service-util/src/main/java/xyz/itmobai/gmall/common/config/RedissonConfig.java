package xyz.itmobai.gmall.common.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * redisson配置信息
 */
@AutoConfigureAfter(RedisAutoConfiguration.class)
@Configuration
public class RedissonConfig {

    @Autowired
    RedisProperties redisProperties;

    @Bean
    public RedissonClient getRedissonClient(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://"+
                                            redisProperties.getHost()+
                                            ":"+
                                            redisProperties.getPort())
                                .setPassword(redisProperties.getPassword());
        return Redisson.create(config);
    }

}