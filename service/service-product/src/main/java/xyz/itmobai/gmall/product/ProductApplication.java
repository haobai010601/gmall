package xyz.itmobai.gmall.product;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import xyz.itmobai.gmall.common.annotation.EnableRedisson;
import xyz.itmobai.gmall.common.config.Swagger2Config;

/**
 * @classname: xyz.itmobai.gmall.product.ProductApplication
 * @author: hao_bai
 * @date: 2022/8/22 18:06
 * @version: 1.0
 */
@Import(Swagger2Config.class)
@MapperScan("xyz.itmobai.gmall.product.mapper")
@SpringCloudApplication
@EnableTransactionManagement
@EnableRedisson
public class ProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class);
    }
}
