package xyz.itmobai.gmall.product.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @classname: xyz.itmobai.gmall.product.config.MybatisPlusConfig
 * @author: hao_bai
 * @date: 2022/8/23 21:55
 * @version: 1.0
 */
@Configuration
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor getMybatisPlusInterceptor(){
        //创建MybatisPlus插件的插件配置
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        //创建并配置分页插件
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        paginationInnerInterceptor.setOverflow(true);
        //将分页插件添加到MybatisPlus插件的插件配置
        mybatisPlusInterceptor.addInnerInterceptor(paginationInnerInterceptor);
        //返回MybatisPlus插件的插件配置实例
        return mybatisPlusInterceptor;
    }
}
