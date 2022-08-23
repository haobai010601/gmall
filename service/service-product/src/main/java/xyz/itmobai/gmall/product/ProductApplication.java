package xyz.itmobai.gmall.product;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @classname: xyz.itmobai.gmall.product.ProductApplication
 * @author: hao_bai
 * @date: 2022/8/22 18:06
 * @version: 1.0
 */
@MapperScan("xyz.itmobai.gmall.product.mapper")
@SpringCloudApplication
public class ProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class);
    }
}
