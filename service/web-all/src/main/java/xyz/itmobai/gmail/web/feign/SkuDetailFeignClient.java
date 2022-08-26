package xyz.itmobai.gmail.web.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.itmobai.gmall.common.result.Result;
import xyz.itmobai.gmall.model.to.SkuDetailTo;

/**
 * @classname: xyz.itmobai.gmail.web.feign.SkuDetailFeignClient
 * @author: hao_bai
 * @date: 2022/8/27 0:09
 * @version: 1.0
 */
@FeignClient("service-item")
@RequestMapping("/api/inner/rpc/item")
public interface SkuDetailFeignClient {

    @GetMapping("/skuDetail/{skuId}")
    Result<SkuDetailTo> getSkuDetail(@PathVariable("skuId")Long skuId);

}
