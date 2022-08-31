package xyz.itmobai.gmail.item.service;

import xyz.itmobai.gmall.model.to.SkuDetailTo;

/**
 * @classname: xyz.itmobai.gmail.item.service.SkuDetailService
 * @author: hao_bai
 * @date: 2022/8/27 0:11
 * @version: 1.0
 */
public interface SkuDetailService {
    SkuDetailTo getSkuDetail(Long skuId);
}
