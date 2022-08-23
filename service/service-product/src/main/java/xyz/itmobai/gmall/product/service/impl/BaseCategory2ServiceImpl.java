package xyz.itmobai.gmall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.itmobai.gmall.model.product.BaseCategory2;
import xyz.itmobai.gmall.product.mapper.BaseCategory2Mapper;
import xyz.itmobai.gmall.product.service.BaseCategory2Service;

import java.util.List;

/**
* @author Hi
* @description 针对表【base_category2(二级分类表)】的数据库操作Service实现
* @createDate 2022-08-22 18:51:45
*/
@Service
public class BaseCategory2ServiceImpl extends ServiceImpl<BaseCategory2Mapper, BaseCategory2>
    implements BaseCategory2Service{

    @Autowired
    private BaseCategory2Mapper baseCategory2Mapper;

    @Override
    public List<BaseCategory2> getCategoryByFatherId(Long category1Id) {
        QueryWrapper<BaseCategory2> wrapper = new QueryWrapper<>();
        wrapper.eq("category1_id",category1Id);
        return baseCategory2Mapper.selectList(wrapper);
    }
}




