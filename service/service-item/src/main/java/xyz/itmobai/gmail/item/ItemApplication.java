package xyz.itmobai.gmail.item;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import xyz.itmobai.gmall.common.annotation.EnableThreadPool;

/**
 * @classname: xyz.itmobai.gmail.item.ItemApplication
 * @author: hao_bai
 * @date: 2022/8/26 22:17
 * @version: 1.0
 */
@EnableThreadPool
@SpringCloudApplication
@EnableFeignClients(basePackages = {
        "xyz.itmobai.gmall.feign.product"
})
public class ItemApplication {
    public static void main(String[] args) {
        SpringApplication.run(ItemApplication.class);
    }
}
