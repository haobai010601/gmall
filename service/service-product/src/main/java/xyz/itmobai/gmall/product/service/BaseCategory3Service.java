package xyz.itmobai.gmall.product.service;


import com.baomidou.mybatisplus.extension.service.IService;
import xyz.itmobai.gmall.model.product.BaseCategory3;
import xyz.itmobai.gmall.model.to.CategoryViewTo;

import java.util.List;

/**
* @author Hi
* @description 针对表【base_category3(三级分类表)】的数据库操作Service
* @createDate 2022-08-22 18:51:45
*/
public interface BaseCategory3Service extends IService<BaseCategory3> {

    List<BaseCategory3> getCategoryByFatherId(Long category2Id);

    CategoryViewTo getCategoryViewTo(Long category3Id);
}
