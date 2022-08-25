package xyz.itmobai.gmall.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.itmobai.gmall.model.product.SpuImage;
import xyz.itmobai.gmall.model.product.SpuInfo;
import xyz.itmobai.gmall.model.product.SpuSaleAttr;
import xyz.itmobai.gmall.model.product.SpuSaleAttrValue;
import xyz.itmobai.gmall.product.mapper.SpuInfoMapper;
import xyz.itmobai.gmall.product.service.SpuImageService;
import xyz.itmobai.gmall.product.service.SpuInfoService;
import xyz.itmobai.gmall.product.service.SpuSaleAttrService;
import xyz.itmobai.gmall.product.service.SpuSaleAttrValueService;

import java.util.List;

/**
* @author Hi
* @description 针对表【spu_info(商品表)】的数据库操作Service实现
* @createDate 2022-08-24 00:17:27
*/
@Service
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoMapper, SpuInfo>
    implements SpuInfoService{

    @Autowired
    SpuInfoMapper spuInfoMapper;
    @Autowired
    SpuImageService spuImageService;
    @Autowired
    SpuSaleAttrService spuSaleAttrService;
    @Autowired
    SpuSaleAttrValueService spuSaleAttrValueService;


    @Override
    @Transactional
    public void saveSpuInfo(SpuInfo spuInfo) {
        //保存SpuInfo
        spuInfoMapper.insert(spuInfo);
        //获取SpuInfo的ID
        Long spuId = spuInfo.getId();

        //获取spuImageList
        List<SpuImage> spuImageList = spuInfo.getSpuImageList();
        //设置每个SpuImage的SpuId
        spuImageList.forEach(spuImage -> {
            spuImage.setSpuId(spuId);
        });
        //批量保存SpuImage
        spuImageService.saveBatch(spuImageList);

        //获取spuSaleAttrList
        List<SpuSaleAttr> spuSaleAttrList = spuInfo.getSpuSaleAttrList();
        //遍历并操作spuSaleAttrList中的spuSaleAttr
        spuSaleAttrList.forEach(spuSaleAttr -> {
            //获取saleAttrName
            String saleAttrName = spuSaleAttr.getSaleAttrName();
            //获取spuSaleAttrValueList
            List<SpuSaleAttrValue> spuSaleAttrValueList = spuSaleAttr.getSpuSaleAttrValueList();
            //遍历并操作spuSaleAttrValueList
            spuSaleAttrValueList.forEach(spuSaleAttrValue -> {
                //设置saleAttrName
                spuSaleAttrValue.setSaleAttrName(saleAttrName);
                //设置spuId
                spuSaleAttrValue.setSpuId(spuId);
            });
            //给spuSaleAttr设置SpuId
            spuSaleAttr.setSpuId(spuId);
            //批量保存spuSaleAttrValue
            spuSaleAttrValueService.saveBatch(spuSaleAttrValueList);
        });
        //批量保存spuSaleAttrList
        spuSaleAttrService.saveBatch(spuSaleAttrList);
    }
}




