package xyz.itmobai.gmall.product.service;


import com.baomidou.mybatisplus.extension.service.IService;
import xyz.itmobai.gmall.model.product.BaseAttrInfo;

import java.util.List;

/**
* @author Hi
* @description 针对表【base_attr_info(属性表)】的数据库操作Service
* @createDate 2022-08-22 22:19:19
*/
public interface BaseAttrInfoService extends IService<BaseAttrInfo> {

    List<BaseAttrInfo> getAttrInfoList(Long category1Id, Long category2Id, Long category3Id);

    void saveAttrInfo(BaseAttrInfo baseAttrInfo);
}
