package xyz.itmobai.gmall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import xyz.itmobai.gmall.model.product.SkuImage;

import java.util.List;

/**
* @author Hi
* @description 针对表【sku_image(库存单元图片表)】的数据库操作Mapper
* @createDate 2022-08-24 00:50:18
* @Entity xyz.itmobai.gmall.product.domain.SkuImage
*/
public interface SkuImageMapper extends BaseMapper<SkuImage> {

    List<SkuImage> getSkuImage(@Param("skuId") Long skuId);

}




