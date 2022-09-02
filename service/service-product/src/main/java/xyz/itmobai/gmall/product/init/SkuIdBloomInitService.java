package xyz.itmobai.gmall.product.init;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.itmobai.gamll.cache.constant.SysRedisConst;
import xyz.itmobai.gmall.product.service.SkuInfoService;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @classname: xyz.itmobai.gmall.product.init.SkuIdBloomInitService
 * @author: hao_bai
 * @date: 2022/9/1 0:26
 * @version: 1.0
 */
@Slf4j
@Service
public class SkuIdBloomInitService {
    @Autowired
    SkuInfoService skuInfoService;
    @Autowired
    RedissonClient redissonClient;

    @PostConstruct
    public void initBloom(){
        log.info("布隆初始化正在进行....");
        List<Long> skuIds  = skuInfoService.findAllSkuId();
        RBloomFilter<Object> filter = redissonClient.getBloomFilter(SysRedisConst.BLOOM_SKUID);
        if (!filter.isExists()) {
            filter.tryInit(5000000,0.00001);
        }
        skuIds.forEach(id ->{
            filter.add(id);
        });
        log.info("布隆初始化已完成");
    }
}
