package xyz.itmobai.gmall.product.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import xyz.itmobai.gamll.cache.constant.SysRedisConst;
import xyz.itmobai.gmall.product.bloom.BloomDataQueryService;
import xyz.itmobai.gmall.product.bloom.BloomOpsService;

/**
 * @classname: xyz.itmobai.gmall.product.schedule.BloomScheduleTask
 * @author: hao_bai
 * @date: 2022/9/1 21:25
 * @version: 1.0
 */
public class BloomScheduleTask {
    @Autowired
    BloomOpsService bloomOpsService;
    @Autowired
    BloomDataQueryService bloomDataQueryService;

    @Scheduled(cron = "0 0 3 ? * 3")
    public void rebuildSkuInfoBloom(){
        bloomOpsService.rebuildBloom(SysRedisConst.BLOOM_SKUID,bloomDataQueryService);
    }
}
