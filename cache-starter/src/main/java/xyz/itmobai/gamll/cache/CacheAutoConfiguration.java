package xyz.itmobai.gamll.cache;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import xyz.itmobai.gamll.cache.aspect.CacheAspect;
import xyz.itmobai.gamll.cache.service.CacheOpsService;
import xyz.itmobai.gamll.cache.service.impl.CacheOpsServiceImpl;

/**
 * @classname: xyz.itmobai.gamll.cache.CacheAutoConfigrtion
 * @author: hao_bai
 * @date: 2022/9/1 18:35
 * @version: 1.0
 */
//启动AOP
@EnableAspectJAutoProxy
@AutoConfigureAfter(RedisAutoConfiguration.class)
@Configuration
public class CacheAutoConfiguration {

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

    @Bean
    public CacheAspect getCacheAspect(){
        return new CacheAspect();
    }

    @Bean
    public CacheOpsService getCacheOpsService(){
        return new CacheOpsServiceImpl();
    }
}
