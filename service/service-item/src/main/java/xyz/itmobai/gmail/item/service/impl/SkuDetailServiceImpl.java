package xyz.itmobai.gmail.item.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.itmobai.gmail.item.feign.SkuDetailFeignClient;
import xyz.itmobai.gmail.item.service.SkuDetailService;
import xyz.itmobai.gmall.common.result.Result;
import xyz.itmobai.gmall.model.product.SkuImage;
import xyz.itmobai.gmall.model.product.SkuInfo;
import xyz.itmobai.gmall.model.product.SpuSaleAttr;
import xyz.itmobai.gmall.model.to.CategoryViewTo;
import xyz.itmobai.gmall.model.to.SkuDetailTo;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

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
    @Autowired
    ThreadPoolExecutor threadPoolExecutor;

    @Override
    public Result<SkuDetailTo> getSkuDetail(Long skuId) {
        SkuDetailTo skuDetailTo = new SkuDetailTo();

        CompletableFuture<SkuInfo> skuInfoFuture = CompletableFuture.supplyAsync(() -> {
            //获取skuInfo对象
            SkuInfo skuInfo = skuDetailFeignClient.getSkuInfo(skuId).getData();
            skuDetailTo.setSkuInfo(skuInfo);
            return skuInfo;
        }, threadPoolExecutor);


        CompletableFuture<Void> categoryViewToFuture = skuInfoFuture.thenAcceptAsync(skuInfo -> {
            //获取分类信息
            Long category3Id = skuInfo.getCategory3Id();
            CategoryViewTo categoryViewTo = skuDetailFeignClient.getCategoryView(category3Id).getData();
            skuDetailTo.setCategoryViewTo(categoryViewTo);
        }, threadPoolExecutor);


        CompletableFuture<Void> skuImageListFuture = skuInfoFuture.thenAcceptAsync(skuInfo -> {
            //获取skuImageList
            List<SkuImage> skuImageList = skuDetailFeignClient.getSkuImages(skuId).getData();
            skuInfo.setSkuImageList(skuImageList);
        }, threadPoolExecutor);

        CompletableFuture<Void> priceFuture = CompletableFuture.runAsync(() -> {
            //获取实时价格
            BigDecimal price = skuDetailFeignClient.getSku1010Price(skuId).getData();
            skuDetailTo.setPrice(price);
        }, threadPoolExecutor);


        CompletableFuture<Void> saleAttrListFuture = skuInfoFuture.thenAcceptAsync(skuInfo -> {
            //获取Spu销售属性集合
            List<SpuSaleAttr> saleAttrList = skuDetailFeignClient
                    .getSkuSaleattrvalues(skuId,skuInfo.getSpuId()).getData();

            skuDetailTo.setSpuSaleAttrList(saleAttrList);
        }, threadPoolExecutor);

        CompletableFuture<Void> valueJsonFuture = skuInfoFuture.thenAcceptAsync(skuInfo -> {
            //查sku组合valueJson
            String valueJson = skuDetailFeignClient.getSKuValueJson(skuInfo.getSpuId()).getData();

            skuDetailTo.setValuesSkuJson(valueJson);
        }, threadPoolExecutor);

        CompletableFuture
                .allOf(categoryViewToFuture,skuImageListFuture,priceFuture,saleAttrListFuture,valueJsonFuture)
                .join();

        return Result.ok(skuDetailTo);
    }

}
