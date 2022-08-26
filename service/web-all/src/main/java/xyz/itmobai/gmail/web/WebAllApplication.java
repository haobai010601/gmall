package xyz.itmobai.gmail.web;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @classname: xyz.itmobai.gmail.web.WebAllApplication
 * @author: hao_bai
 * @date: 2022/8/26 14:58
 * @version: 1.0
 */
@SpringCloudApplication
@EnableFeignClients
public class WebAllApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebAllApplication.class);
    }
}
