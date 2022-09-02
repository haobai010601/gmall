package xyz.itmobai.gamll.cache.service;

import java.lang.reflect.Type;

/**
 * @classname: xyz.itmobai.gmail.item.cache.CacheOpsService
 * @author: hao_bai
 * @date: 2022/8/31 23:53
 * @version: 1.0
 */
public interface CacheOpsService {
    <T> T getCacheData(String cacheKey, Class<T> clazz);

    <T> T getCacheData(String cacheKey, Type type);

    Boolean bloomContains(Long skuId);

    Boolean bloomContains(String bloomName,Object bloomValue);

    Boolean tryLock(String s);

    void unLock(String lockKey);

    void savaCacheData(String cacheKey, Object obj);

    void deleteCache(String cacheKey);

    void delayDeleteCache(String cacheKey);

}
