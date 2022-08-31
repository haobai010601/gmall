package xyz.itmobai.gmail.item.cache;

/**
 * @classname: xyz.itmobai.gmail.item.cache.CacheOpsService
 * @author: hao_bai
 * @date: 2022/8/31 23:53
 * @version: 1.0
 */
public interface CacheOpsService {
    public <T> T getCacheData(String cacheKey, Class<T> clazz);

    Boolean bloomContains(Long skuId);

    Boolean tryLock(String s);



    void unLock(String lockKey);

    void savaCacheData(String cacheKey, Object obj);
}
