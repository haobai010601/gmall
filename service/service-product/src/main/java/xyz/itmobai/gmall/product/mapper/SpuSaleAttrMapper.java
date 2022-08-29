package xyz.itmobai.gmall.product.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import xyz.itmobai.gmall.model.product.SpuSaleAttr;
import xyz.itmobai.gmall.model.to.ValueSkuJsonTo;

import java.util.List;

/**
* @author Hi
* @description 针对表【spu_sale_attr(spu销售属性)】的数据库操作Mapper
* @createDate 2022-08-24 00:17:27
* @Entity xyz.itmobai.gmall.product.domain.SpuSaleAttr
*/
public interface SpuSaleAttrMapper extends BaseMapper<SpuSaleAttr> {

    List<SpuSaleAttr> getSpuSaleAttrListBySpuId(@Param("spuId") Long spuId);

    List<SpuSaleAttr> getSaleAttrAndValueMarkSku(@Param("spuId") Long spuId, @Param("skuId") Long skuId);

    List<ValueSkuJsonTo> getAllSkuSaleAttrValueJson(@Param("spuId") Long spuId);
}




