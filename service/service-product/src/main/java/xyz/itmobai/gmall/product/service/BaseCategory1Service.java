package xyz.itmobai.gmall.product.service;


import com.baomidou.mybatisplus.extension.service.IService;
import xyz.itmobai.gmall.model.product.BaseCategory1;
import xyz.itmobai.gmall.model.to.CategoryTreeTo;

import java.util.List;

/**
* @author Hi
* @description 针对表【base_category1(一级分类表)】的数据库操作Service
* @createDate 2022-08-22 18:51:45
*/
public interface BaseCategory1Service extends IService<BaseCategory1> {

    List<CategoryTreeTo> getAllCategoryWithTree();

}
