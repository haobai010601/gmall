package xyz.itmobai.gmail.item.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.itmobai.gmail.item.feign.SkuDetailFeignClient;
import xyz.itmobai.gmail.item.service.SkuDetailService;
import xyz.itmobai.gmall.common.result.Result;
import xyz.itmobai.gmall.model.to.SkuDetailTo;

/**
 * @classname: xyz.itmobai.gmail.item.service.impl.SkuDetailServiceImpl
 * @author: hao_bai
 * @date: 2022/8/27 0:13
 * @version: 1.0
 */
@Service
public class SkuDetailServiceImpl implements SkuDetailService {
    @Autowired
    SkuDetailFeignClient skuDetailFeignClient;

    @Override
    public Result<SkuDetailTo> getSkuDetail(Long skuId) {
        return skuDetailFeignClient.getSkuDetail(skuId);
    }
}
