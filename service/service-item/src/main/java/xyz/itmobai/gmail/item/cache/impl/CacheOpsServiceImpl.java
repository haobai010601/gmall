package xyz.itmobai.gmail.item.cache.impl;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import xyz.itmobai.gmail.item.cache.CacheOpsService;
import xyz.itmobai.gmall.common.constant.SysRedisConst;
import xyz.itmobai.gmall.common.util.Jsons;

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
    public Boolean bloomContains(Long skuId) {
        RBloomFilter<Long> bloomFilter = redissonClient.getBloomFilter(SysRedisConst.BLOOM_SKUID);
        return bloomFilter.contains(skuId);
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
}
