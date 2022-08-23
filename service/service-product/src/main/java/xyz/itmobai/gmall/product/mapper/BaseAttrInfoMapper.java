package xyz.itmobai.gmall.product.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import xyz.itmobai.gmall.model.product.BaseAttrInfo;

import java.util.List;

/**
* @author Hi
* @description 针对表【base_attr_info(属性表)】的数据库操作Mapper
* @createDate 2022-08-22 22:19:19
* @Entity xyz.itmobai.gmall.product.domain.BaseAttrInfo
*/
public interface BaseAttrInfoMapper extends BaseMapper<BaseAttrInfo> {

    List<BaseAttrInfo> selectBaseAttrInfoList(@Param("category1Id") Long category1Id,
                                              @Param("category2Id") Long category2Id,
                                              @Param("category3Id") Long category3Id);
}




