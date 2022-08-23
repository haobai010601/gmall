package xyz.itmobai.gmall.product.service;


import com.baomidou.mybatisplus.extension.service.IService;
import xyz.itmobai.gmall.model.product.BaseAttrValue;

import java.util.List;

/**
* @author Hi
* @description 针对表【base_attr_value(属性值表)】的数据库操作Service
* @createDate 2022-08-22 23:57:42
*/
public interface BaseAttrValueService extends IService<BaseAttrValue> {

    List<BaseAttrValue> getAttrValueList(Long attrId);

}
