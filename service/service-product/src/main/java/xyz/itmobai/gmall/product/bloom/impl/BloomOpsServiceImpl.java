package xyz.itmobai.gmall.product.bloom.impl;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.itmobai.gmall.product.bloom.BloomDataQueryService;
import xyz.itmobai.gmall.product.bloom.BloomOpsService;

import java.util.List;

/**
 * @classname: xyz.itmobai.gmall.product.bloom.impl.BloomOpsServiceImpl
 * @author: hao_bai
 * @date: 2022/9/1 21:00
 * @version: 1.0
 */
@Service
public class BloomOpsServiceImpl implements BloomOpsService {

    @Autowired
    RedissonClient redissonClient;

    @Override
    public void rebuildBloom(String bloomName, BloomDataQueryService dataQueryService) {
        //获取老的布隆过滤器
        RBloomFilter<Object> oldBloomFilter = redissonClient.getBloomFilter(bloomName);
        //拼接新布隆的名称
        String newBloomName = bloomName + "_new";
        //创建新的布隆过滤器
        RBloomFilter<Object> newBloomFilter = redissonClient.getBloomFilter(newBloomName);
        //获取老布隆容错率
        double falseProbability = oldBloomFilter.getFalseProbability();
        //获取老布隆大小
        long expectedInsertions = oldBloomFilter.getExpectedInsertions();
        //初始化新布隆
        newBloomFilter.tryInit(expectedInsertions, falseProbability);
        //获取需要放入布隆的参数
        List list = dataQueryService.queryData();
        //将参数放入新布隆中
        list.forEach(data ->{
            newBloomFilter.add(data);
        });

        //临时布隆名称
        String tempBloomName = "bloom_temp";

        //将老布隆改名为临时
        oldBloomFilter.rename(tempBloomName);
        //将新布隆改为要使用的布隆名称
        newBloomFilter.rename(bloomName);

        //删除老布隆
        oldBloomFilter.deleteAsync();
        //删除临时布隆
        redissonClient.getBloomFilter(tempBloomName).deleteAsync();
    }
}
