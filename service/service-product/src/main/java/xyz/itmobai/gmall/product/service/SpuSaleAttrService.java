package xyz.itmobai.gmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.itmobai.gmall.model.product.SpuSaleAttr;

import java.util.List;

/**
* @author Hi
* @description 针对表【spu_sale_attr(spu销售属性)】的数据库操作Service
* @createDate 2022-08-24 00:17:27
*/
public interface SpuSaleAttrService extends IService<SpuSaleAttr> {

    List<SpuSaleAttr> getSpuSaleAttrListBySpuId(Long spuId);

    List<SpuSaleAttr> getSaleAttrAndValueMarkSku(Long spuId, Long skuId);

    String getAllSkuSaleAttrValueJson(Long spuId);
}
