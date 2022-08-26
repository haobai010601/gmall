package xyz.itmobai.gmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.itmobai.gmall.model.product.SkuInfo;
import xyz.itmobai.gmall.model.to.SkuDetailTo;

/**
* @author Hi
* @description 针对表【sku_info(库存单元表)】的数据库操作Service
* @createDate 2022-08-24 00:50:19
*/
public interface SkuInfoService extends IService<SkuInfo> {

    void onSale(Long skuId);

    void cancelSale(Long skuId);

    void saveSkuInfo(SkuInfo skuInfo);

    SkuDetailTo getSkuDetail(Long skuId);
}
