package xyz.itmobai.gmail.item.api;

/**
 * @classname: xyz.itmobai.gmail.item.api.SkuDetailApi
 * @author: hao_bai
 * @date: 2022/8/27 0:08
 * @version: 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.itmobai.gmail.item.service.SkuDetailService;
import xyz.itmobai.gmall.common.result.Result;
import xyz.itmobai.gmall.model.to.SkuDetailTo;

@RestController
@RequestMapping("/api/inner/rpc/item")
public class SkuDetailApi {

    @Autowired
    SkuDetailService skuDetailServices;

    @GetMapping("/skuDetail/{skuId}")
    public Result<SkuDetailTo> getSkuDetail(@PathVariable("skuId")Long skuId){
        SkuDetailTo skuDetail = skuDetailServices.getSkuDetail(skuId);
        return Result.ok(skuDetail);
    }

}
