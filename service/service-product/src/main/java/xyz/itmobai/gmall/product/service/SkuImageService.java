package xyz.itmobai.gmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.itmobai.gmall.model.product.SkuImage;

import java.util.List;

/**
* @author Hi
* @description 针对表【sku_image(库存单元图片表)】的数据库操作Service
* @createDate 2022-08-24 00:50:18
*/
public interface SkuImageService extends IService<SkuImage> {

    List<SkuImage> getSkuImage(Long skuId);
}
