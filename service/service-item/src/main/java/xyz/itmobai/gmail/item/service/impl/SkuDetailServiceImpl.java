package xyz.itmobai.gmail.item.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.itmobai.gamll.cache.annotations.GmallCache;
import xyz.itmobai.gamll.cache.constant.SysRedisConst;
import xyz.itmobai.gamll.cache.service.CacheOpsService;
import xyz.itmobai.gmail.item.service.SkuDetailService;
import xyz.itmobai.gmall.feign.product.ProductFeignClient;
import xyz.itmobai.gmall.model.product.SkuImage;
import xyz.itmobai.gmall.model.product.SkuInfo;
import xyz.itmobai.gmall.model.product.SpuSaleAttr;
import xyz.itmobai.gmall.model.to.CategoryViewTo;
import xyz.itmobai.gmall.model.to.SkuDetailTo;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @classname: xyz.itmobai.gmail.item.service.impl.SkuDetailServiceImpl
 * @author: hao_bai
 * @date: 2022/8/27 0:13
 * @version: 1.0
 */
@Slf4j
@Service
public class SkuDetailServiceImpl implements SkuDetailService {
    @Autowired
    ProductFeignClient productFeignClient;
    @Autowired
    ThreadPoolExecutor threadPoolExecutor;
    @Autowired
    CacheOpsService cacheOpsService;

    /**
    * 查询SkuDetailTo
    * 本体自带缓存版
    */
    public SkuDetailTo getSkuDetail_cache(Long skuId) {
        String cacheKey = SysRedisConst.SKU_INFO_PREFIX + skuId;
        String lockKey = SysRedisConst.LOCK_SKU_DETAIL + skuId;

        SkuDetailTo skuDetailTo = cacheOpsService.getCacheData(cacheKey,SkuDetailTo.class);
        if (skuDetailTo == null){
            Boolean contain = cacheOpsService.bloomContains(skuId);
            if (!contain){
                log.info("[{}]商品 - 布隆判定没有，检测到隐藏的攻击风险....",skuId);
                return null;
            }
            Boolean lock = cacheOpsService.tryLock(lockKey);
            if (lock){
                skuDetailTo = getSkuDetailToFromRpc(skuId);
                cacheOpsService.savaCacheData(cacheKey,skuDetailTo);
                cacheOpsService.unLock(lockKey);
                return skuDetailTo;
            }else{
                try {TimeUnit.SECONDS.sleep(1);}catch(InterruptedException e){e.printStackTrace();}
                skuDetailTo = cacheOpsService.getCacheData(cacheKey,SkuDetailTo.class);
                return skuDetailTo;
            }
        }
        //SkuDetailTo skuDetailTo = getSkuDetailToFromRpc(skuId);
        return skuDetailTo;
    }

    /**
     * 查询SkuDetailTo
     * 通过注解实现缓存版
     */
    @GmallCache(
            cacheKey =SysRedisConst.SKU_INFO_PREFIX+"#{#params[0]}",
                bloomName = SysRedisConst.BLOOM_SKUID,
                bloomValue = "#{#params[0]}",
                lockName = SysRedisConst.LOCK_SKU_DETAIL+"#{#params[0]}"
    )
    @Override
    public SkuDetailTo getSkuDetail(Long skuId) {
        return getSkuDetailToFromRpc(skuId);
    }

    private SkuDetailTo getSkuDetailToFromRpc(Long skuId) {
        SkuDetailTo skuDetailTo = new SkuDetailTo();

        CompletableFuture<SkuInfo> skuInfoFuture = CompletableFuture.supplyAsync(() -> {
            //获取skuInfo对象
            SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId).getData();
            skuDetailTo.setSkuInfo(skuInfo);
            return skuInfo;
        }, threadPoolExecutor);

        CompletableFuture<Void> categoryViewToFuture = skuInfoFuture.thenAcceptAsync(skuInfo -> {
            //获取分类信息
            Long category3Id = skuInfo.getCategory3Id();
            CategoryViewTo categoryViewTo = productFeignClient.getCategoryView(category3Id).getData();
            skuDetailTo.setCategoryViewTo(categoryViewTo);
        }, threadPoolExecutor);

        CompletableFuture<Void> skuImageListFuture = skuInfoFuture.thenAcceptAsync(skuInfo -> {
            //获取skuImageList
            List<SkuImage> skuImageList = productFeignClient.getSkuImages(skuId).getData();
            skuInfo.setSkuImageList(skuImageList);
        }, threadPoolExecutor);

        CompletableFuture<Void> priceFuture = CompletableFuture.runAsync(() -> {
            //获取实时价格
            BigDecimal price = productFeignClient.getSku1010Price(skuId).getData();
            skuDetailTo.setPrice(price);
        }, threadPoolExecutor);


        CompletableFuture<Void> saleAttrListFuture = skuInfoFuture.thenAcceptAsync(skuInfo -> {
            //获取Spu销售属性集合
            List<SpuSaleAttr> saleAttrList = productFeignClient
                    .getSkuSaleattrvalues(skuId,skuInfo.getSpuId()).getData();

            skuDetailTo.setSpuSaleAttrList(saleAttrList);
        }, threadPoolExecutor);

        CompletableFuture<Void> valueJsonFuture = skuInfoFuture.thenAcceptAsync(skuInfo -> {
            //查sku组合valueJson
            String valueJson = productFeignClient.getSKuValueJson(skuInfo.getSpuId()).getData();

            skuDetailTo.setValuesSkuJson(valueJson);
        }, threadPoolExecutor);

        CompletableFuture
                .allOf(categoryViewToFuture,skuImageListFuture,priceFuture,saleAttrListFuture,valueJsonFuture)
                .join();
        return skuDetailTo;
    }

}
