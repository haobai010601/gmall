package xyz.itmobai.gmall.product.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.itmobai.gmall.common.result.Result;
import xyz.itmobai.gmall.model.to.SkuDetailTo;
import xyz.itmobai.gmall.product.service.SkuInfoService;

/**
 * @classname: xyz.itmobai.gmall.product.api.SkuDetailApi
 * @author: hao_bai
 * @date: 2022/8/27 0:17
 * @version: 1.0
 */
@RestController
@RequestMapping("/api/inner/rpc/product/skuDetail")
public class SkuDetailApi {
    @Autowired
    SkuInfoService skuInfoService;


    @GetMapping("/{skuId}")
    public Result<SkuDetailTo> getSkuDetail(@PathVariable("skuId")Long skuId){
        SkuDetailTo skuDetailTo = skuInfoService.getSkuDetail(skuId);
        return Result.ok(skuDetailTo);
    }

}
