package xyz.itmobai.gmall.product.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.itmobai.gamll.cache.annotations.GmallCacheUpdate;
import xyz.itmobai.gamll.cache.constant.SysRedisConst;
import xyz.itmobai.gmall.model.product.*;
import xyz.itmobai.gmall.model.to.CategoryViewTo;
import xyz.itmobai.gmall.model.to.SkuDetailTo;
import xyz.itmobai.gmall.product.mapper.SkuInfoMapper;
import xyz.itmobai.gmall.product.service.*;

import java.math.BigDecimal;
import java.util.List;

/**
* @author Hi
* @description 针对表【sku_info(库存单元表)】的数据库操作Service实现
* @createDate 2022-08-24 00:50:19
*/
@Service
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfo>
    implements SkuInfoService {

    @Autowired
    SkuInfoMapper skuInfoMapper;
    @Autowired
    SkuImageService skuImageService;
    @Autowired
    SkuAttrValueService skuAttrValueServices;
    @Autowired
    SkuSaleAttrValueService skuSaleAttrValueService;
    @Autowired
    BaseCategory3Service baseCategory3Service;
    @Autowired
    SpuSaleAttrService spuSaleAttrService;
    @Autowired
    RedissonClient redissonClient;

    @Override
    public void onSale(Long skuId) {
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setId(skuId);
        skuInfo.setIsSale(1);
        skuInfoMapper.updateById(skuInfo);
    }

    @Override
    public void cancelSale(Long skuId) {
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setId(skuId);
        skuInfo.setIsSale(0);
        skuInfoMapper.updateById(skuInfo);
    }

    @Override
    @Transactional
    public void saveSkuInfo(SkuInfo skuInfo) {
        //保存skuInfo
        skuInfoMapper.insert(skuInfo);
        //获取skuInfo的id
        Long skuId = skuInfo.getId();

        //获取skuImageList
        List<SkuImage> skuImageList = skuInfo.getSkuImageList();
        //遍历并给每个skuImage赋skuId值
        skuImageList.forEach(skuImage -> {
            skuImage.setSkuId(skuId);
        });
        //批量保存skuImage
        skuImageService.saveBatch(skuImageList);

        //获取skuAttrValueList
        List<SkuAttrValue> skuAttrValueList = skuInfo.getSkuAttrValueList();
        //遍历并给每个skuAttrValue赋skuId值
        skuAttrValueList.forEach(skuAttrValue -> {
            skuAttrValue.setSkuId(skuId);
        });
        //批量保存skuAttrValue
        skuAttrValueServices.saveBatch(skuAttrValueList);

        //获取skuSaleAttrValueList
        List<SkuSaleAttrValue> skuSaleAttrValueList = skuInfo.getSkuSaleAttrValueList();
        //遍历并给每个skuSaleAttrValue赋skuId和spuId值
        skuSaleAttrValueList.forEach(skuSaleAttrValue -> {
            skuSaleAttrValue.setSkuId(skuId);
            skuSaleAttrValue.setSpuId(skuInfo.getSpuId());
        });
        //批量保存skuSaleAttrValue
        skuSaleAttrValueService.saveBatch(skuSaleAttrValueList);

        //向布隆中添加数据
        RBloomFilter<Long> bloomFilter = redissonClient.getBloomFilter(SysRedisConst.BLOOM_SKUID);
        bloomFilter.add(skuId);
    }

    @Override
    public SkuDetailTo getSkuDetail(Long skuId) {
        SkuDetailTo skuDetailTo = new SkuDetailTo();
        //获取skuInfo对象
        SkuInfo skuInfo = skuInfoMapper.selectById(skuId);
        //获取它属于哪个C3Id
        Long category3Id = skuInfo.getCategory3Id();
        //获取分类信息
        CategoryViewTo categoryViewTo = baseCategory3Service
                .getCategoryViewTo(category3Id);
        //获取skuImageList
        List<SkuImage> skuImageList = skuImageService.getSkuImage(skuId);
        skuInfo.setSkuImageList(skuImageList);
        //获取实时价格
        BigDecimal price = get1010Price(skuId);
        //获取Spu销售属性集合
        List<SpuSaleAttr> saleAttrList = spuSaleAttrService
                .getSaleAttrAndValueMarkSku(skuInfo.getSpuId(),skuId);
        //查sku组合valueJson
        String valueJson = spuSaleAttrService.getAllSkuSaleAttrValueJson(skuInfo.getSpuId());

        skuDetailTo.setSkuInfo(skuInfo);
        skuDetailTo.setCategoryViewTo(categoryViewTo);
        skuDetailTo.setPrice(price);
        skuDetailTo.setSpuSaleAttrList(saleAttrList);
        skuDetailTo.setValuesSkuJson(valueJson);
        return skuDetailTo;
    }

    public BigDecimal get1010Price(Long skuId) {
        BigDecimal price = skuInfoMapper.get1010Price(skuId);
        return price;
    }

    @Override
    public List<Long> findAllSkuId() {
       return skuInfoMapper.getAllSkuId();
    }

    @Override
    @GmallCacheUpdate(cacheKey = SysRedisConst.SKU_INFO_PREFIX + "#{#params[0].id}")
    public void updateSkuInfo(SkuInfo skuInfo) {
        skuInfoMapper.updateById(skuInfo);
    }
}




