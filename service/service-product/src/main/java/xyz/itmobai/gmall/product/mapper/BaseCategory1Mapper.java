package xyz.itmobai.gmall.product.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import xyz.itmobai.gmall.model.product.BaseCategory1;
import xyz.itmobai.gmall.model.to.CategoryTreeTo;

import java.util.List;

/**
* @author Hi
* @description 针对表【base_category1(一级分类表)】的数据库操作Mapper
* @createDate 2022-08-22 18:51:45
* @Entity xyz.itmobai.gmall.product.domain.BaseCategory1
*/
public interface BaseCategory1Mapper extends BaseMapper<BaseCategory1> {

    List<CategoryTreeTo> getAllCategoryWithTree();

}




