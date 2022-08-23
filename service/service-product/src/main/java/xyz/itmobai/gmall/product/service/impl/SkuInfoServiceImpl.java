package xyz.itmobai.gmall.product.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.itmobai.gmall.model.product.SkuInfo;
import xyz.itmobai.gmall.product.mapper.SkuInfoMapper;
import xyz.itmobai.gmall.product.service.SkuInfoService;

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
}




