package xyz.itmobai.gmail.item.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.itmobai.gmall.common.result.Result;
import xyz.itmobai.gmall.model.product.SkuImage;
import xyz.itmobai.gmall.model.product.SkuInfo;
import xyz.itmobai.gmall.model.product.SpuSaleAttr;
import xyz.itmobai.gmall.model.to.CategoryViewTo;
import xyz.itmobai.gmall.model.to.SkuDetailTo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @classname: xyz.itmobai.gmail.web.feign.SkuDetailFeignClient
 * @author: hao_bai
 * @date: 2022/8/27 0:09
 * @version: 1.0
 */
@FeignClient("service-product")
@RequestMapping("/api/inner/rpc/product")
public interface SkuDetailFeignClient {

    @GetMapping("/skuDetail/{skuId}")
    Result<SkuDetailTo> getSkuDetail(@PathVariable("skuId")Long skuId);

    /**
     * 查询sku的基本信息
     * @param skuId
     * @return
     */
    @GetMapping("/skuDetail/info/{skuId}")
    Result<SkuInfo> getSkuInfo(@PathVariable("skuId") Long skuId);

    /**
     * 查询sku的图片信息
     * @param skuId
     * @return
     */
    @GetMapping("/skuDetail/images/{skuId}")
    Result<List<SkuImage>> getSkuImages(@PathVariable("skuId")Long skuId);

    /**
     * 查询sku的实时价格
     * @param skuId
     * @return
     */
    @GetMapping("/skuDetail/price/{skuId}")
    Result<BigDecimal> getSku1010Price(@PathVariable("skuId")Long skuId);

    /**
     * 查询sku对应的spu定义的所有销售属性名和值。并且标记出当前sku是哪个
     * @param skuId
     * @param spuId
     * @return
     */
    @GetMapping("/skuDetail/saleattrvalues/{skuId}/{spuId}")
    Result<List<SpuSaleAttr>> getSkuSaleattrvalues(@PathVariable("skuId") Long skuId,
                                                   @PathVariable("spuId") Long spuId);

    /**
     * 查sku组合 valueJson
     * @param spuId
     * @return
     */
    @GetMapping("/skuDetail/valuejson/{spuId}")
    Result<String> getSKuValueJson(@PathVariable("spuId") Long spuId);

    /**
     * 查分类
     * @param c3Id
     * @return
     */
    @GetMapping("/skuDetail/categoryview/{c3Id}")
    Result<CategoryViewTo> getCategoryView(@PathVariable("c3Id") Long c3Id);


}
