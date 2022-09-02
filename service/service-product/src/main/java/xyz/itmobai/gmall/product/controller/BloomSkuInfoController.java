package xyz.itmobai.gmall.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.itmobai.gamll.cache.constant.SysRedisConst;
import xyz.itmobai.gmall.common.result.Result;
import xyz.itmobai.gmall.product.bloom.BloomDataQueryService;
import xyz.itmobai.gmall.product.bloom.BloomOpsService;

/**
 * @classname: xyz.itmobai.gmall.product.controller.BloomSkuInfoController
 * @author: hao_bai
 * @date: 2022/9/1 21:16
 * @version: 1.0
 */
@RequestMapping("/admin/product")
@RestController
public class BloomSkuInfoController {

    @Autowired
    BloomOpsService bloomOpsService;
    @Autowired
    BloomDataQueryService bloomDataQueryService;

    @GetMapping("/rebuild/skuInfoBloom")
    public Result rebuildSkuInfoBloom(){
        bloomOpsService.rebuildBloom(SysRedisConst.BLOOM_SKUID,bloomDataQueryService);
        return Result.ok();
    }


}
