package xyz.itmobai.gmall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.itmobai.gmall.model.product.BaseAttrValue;
import xyz.itmobai.gmall.product.mapper.BaseAttrValueMapper;
import xyz.itmobai.gmall.product.service.BaseAttrValueService;

import java.util.List;

/**
* @author Hi
* @description 针对表【base_attr_value(属性值表)】的数据库操作Service实现
* @createDate 2022-08-22 23:57:42
*/
@Service
public class BaseAttrValueServiceImpl extends ServiceImpl<BaseAttrValueMapper, BaseAttrValue> implements BaseAttrValueService{

    @Autowired
    BaseAttrValueMapper baseAttrValueMapper;

    @Override
    public List<BaseAttrValue> getAttrValueList(Long attrId) {
        QueryWrapper<BaseAttrValue> wrapper = new QueryWrapper<>();
        wrapper.eq("attr_id",attrId);
        return baseAttrValueMapper.selectList(wrapper);
    }
}




