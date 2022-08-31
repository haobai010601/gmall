package xyz.itmobai.gmall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import xyz.itmobai.gmall.model.product.SkuInfo;

import java.math.BigDecimal;
import java.util.List;

/**
* @author Hi
* @description 针对表【sku_info(库存单元表)】的数据库操作Mapper
* @createDate 2022-08-24 00:50:18
* @Entity xyz.itmobai.gmall.product.domain.SkuInfo
*/
public interface SkuInfoMapper extends BaseMapper<SkuInfo> {

    BigDecimal get1010Price(@Param("skuId") Long skuId);

    List<Long> getAllSkuId();
}




