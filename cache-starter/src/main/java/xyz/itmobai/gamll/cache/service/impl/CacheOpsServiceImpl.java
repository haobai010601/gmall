package xyz.itmobai.gamll.cache.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import xyz.itmobai.gamll.cache.constant.SysRedisConst;
import xyz.itmobai.gamll.cache.service.CacheOpsService;
import xyz.itmobai.gamll.cache.util.Jsons;

import java.lang.reflect.Type;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @classname: xyz.itmobai.gmail.item.cache.impl.CacheOpsServiceImpl
 * @author: hao_bai
 * @date: 2022/8/31 23:53
 * @version: 1.0
 */
@Service
public class CacheOpsServiceImpl implements CacheOpsService {
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    RedissonClient redissonClient;

    ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(5);

    @Override
    public <T> T getCacheData(String cacheKey, Class<T> clazz) {
        String dataJson = redisTemplate.opsForValue().get(cacheKey);
        if (StringUtils.isEmpty(dataJson) || SysRedisConst.NULL_VAL.equals(dataJson)){
            return null;
        }
        T t = Jsons.toObj(dataJson, clazz);
        return t;
    }

    @Override
    public <T> T getCacheData(String cacheKey, Type type) {
        String dataJson = redisTemplate.opsForValue().get(cacheKey);
        if (StringUtils.isEmpty(dataJson) || SysRedisConst.NULL_VAL.equals(dataJson)){
            return null;
        }
        T t = Jsons.toObj(dataJson, new TypeReference<T>() {
            @Override
            public Type getType() {
                return type;
            }
        });
        return t;
    }

    @Override
    public Boolean bloomContains(Long skuId) {
        RBloomFilter<Long> bloomFilter = redissonClient.getBloomFilter(SysRedisConst.BLOOM_SKUID);
        return bloomFilter.contains(skuId);
    }

    @Override
    public Boolean bloomContains(String bloomName, Object bloomValue) {
        RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(bloomName);
        return bloomFilter.contains(bloomValue);
    }

    @Override
    public Boolean tryLock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        boolean tryLock = lock.tryLock();
        return tryLock;
    }

    @Override
    public void unLock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.unlock();
    }

    @Override
    public void savaCacheData(String cacheKey, Object obj) {
        if (obj == null) {
            redisTemplate.opsForValue().set(cacheKey,
                    SysRedisConst.NULL_VAL,
                    SysRedisConst.NULL_VAL_TTL,
                    TimeUnit.SECONDS);
        }else {
            redisTemplate.opsForValue().set(cacheKey,
                    Jsons.toStr(obj),
                    SysRedisConst.SKUDETAIL_TTL,
                    TimeUnit.SECONDS);
        }
    }

    @Override
    public void deleteCache(String cacheKey) {
        redisTemplate.delete(cacheKey);
    }

    @Override
    public void delayDeleteCache(String cacheKey) {
        redisTemplate.delete(cacheKey);
        scheduledExecutor.schedule(()->{
            redisTemplate.delete(cacheKey);
        },10L,TimeUnit.SECONDS);
    }
}
