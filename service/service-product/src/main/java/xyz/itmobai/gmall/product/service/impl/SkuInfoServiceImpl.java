package xyz.itmobai.gmall.product.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.itmobai.gmall.model.product.SkuAttrValue;
import xyz.itmobai.gmall.model.product.SkuImage;
import xyz.itmobai.gmall.model.product.SkuInfo;
import xyz.itmobai.gmall.model.product.SkuSaleAttrValue;
import xyz.itmobai.gmall.product.mapper.SkuInfoMapper;
import xyz.itmobai.gmall.product.service.SkuAttrValueService;
import xyz.itmobai.gmall.product.service.SkuImageService;
import xyz.itmobai.gmall.product.service.SkuInfoService;
import xyz.itmobai.gmall.product.service.SkuSaleAttrValueService;

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
    }
}




