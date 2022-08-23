package xyz.itmobai.gmall.product.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @classname: xyz.itmobai.gmall.product.config.MinioConfig
 * @author: hao_bai
 * @date: 2022/8/23 23:21
 * @version: 1.0
 */
@Data
@Configuration
@ConfigurationProperties(prefix="minio.base")
public class MinioConfig {

    private String url;

    private String accessKey;

    private String secretKey;

}
