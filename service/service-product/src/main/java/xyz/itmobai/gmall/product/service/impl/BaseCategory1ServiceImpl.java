package xyz.itmobai.gmall.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.itmobai.gmall.model.product.BaseCategory1;
import xyz.itmobai.gmall.model.to.CategoryTreeTo;
import xyz.itmobai.gmall.product.mapper.BaseCategory1Mapper;
import xyz.itmobai.gmall.product.service.BaseCategory1Service;

import java.util.List;

/**
* @author Hi
* @description 针对表【base_category1(一级分类表)】的数据库操作Service实现
* @createDate 2022-08-22 18:51:45
*/
@Service
public class BaseCategory1ServiceImpl extends ServiceImpl<BaseCategory1Mapper, BaseCategory1>
    implements BaseCategory1Service{

    @Autowired
    BaseCategory1Mapper baseCategory1Mapper;

    @Override
    public List<CategoryTreeTo> getAllCategoryWithTree() {
        return baseCategory1Mapper.getAllCategoryWithTree();
    }
}




