package xyz.itmobai.gmall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.itmobai.gmall.model.product.BaseCategory3;
import xyz.itmobai.gmall.model.to.CategoryViewTo;
import xyz.itmobai.gmall.product.mapper.BaseCategory3Mapper;
import xyz.itmobai.gmall.product.service.BaseCategory3Service;

import java.util.List;

/**
* @author Hi
* @description 针对表【base_category3(三级分类表)】的数据库操作Service实现
* @createDate 2022-08-22 18:51:45
*/
@Service
public class BaseCategory3ServiceImpl extends ServiceImpl<BaseCategory3Mapper, BaseCategory3>
    implements BaseCategory3Service{

    @Autowired
    private BaseCategory3Mapper baseCategory3Mapper;

    @Override
    public List<BaseCategory3> getCategoryByFatherId(Long category2Id) {
        QueryWrapper<BaseCategory3> wrapper = new QueryWrapper<>();
        wrapper.eq("category2_id",category2Id);
        return baseCategory3Mapper.selectList(wrapper);
    }

    @Override
    public CategoryViewTo getCategoryViewTo(Long category3Id) {
        return baseCategory3Mapper.getCategoryViewTo(category3Id);
    }
}




