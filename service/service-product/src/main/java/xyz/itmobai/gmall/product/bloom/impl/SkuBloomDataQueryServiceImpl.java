package xyz.itmobai.gmall.product.bloom.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.itmobai.gmall.product.bloom.BloomDataQueryService;
import xyz.itmobai.gmall.product.service.SkuInfoService;

import java.util.List;

/**
 * @classname: xyz.itmobai.gmall.product.bloom.impl.SkuBloomDataQueryServiceImpl
 * @author: hao_bai
 * @date: 2022/9/1 21:01
 * @version: 1.0
 */
@Service
public class SkuBloomDataQueryServiceImpl implements BloomDataQueryService {

    @Autowired
    SkuInfoService skuInfoService;

    @Override
    public List queryData() {
        List<Long> allSkuId = skuInfoService.findAllSkuId();
        return allSkuId;
    }
}
