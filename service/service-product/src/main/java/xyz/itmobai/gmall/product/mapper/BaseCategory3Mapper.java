package xyz.itmobai.gmall.product.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import xyz.itmobai.gmall.model.product.BaseCategory3;
import xyz.itmobai.gmall.model.to.CategoryViewTo;

/**
* @author Hi
* @description 针对表【base_category3(三级分类表)】的数据库操作Mapper
* @createDate 2022-08-22 18:51:45
* @Entity xyz.itmobai.gmall.product.domain.BaseCategory3
*/
public interface BaseCategory3Mapper extends BaseMapper<BaseCategory3> {

    CategoryViewTo getCategoryViewTo(@Param("category3Id") Long category3Id);
}




