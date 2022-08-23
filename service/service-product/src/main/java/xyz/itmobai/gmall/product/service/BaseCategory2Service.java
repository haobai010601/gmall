package xyz.itmobai.gmall.product.service;


import com.baomidou.mybatisplus.extension.service.IService;
import xyz.itmobai.gmall.model.product.BaseCategory2;

import java.util.List;

/**
* @author Hi
* @description 针对表【base_category2(二级分类表)】的数据库操作Service
* @createDate 2022-08-22 18:51:45
*/
public interface BaseCategory2Service extends IService<BaseCategory2> {

    List<BaseCategory2> getCategoryByFatherId(Long category1Id);
}
