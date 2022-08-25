package xyz.itmobai.gmall.product.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @classname: xyz.itmobai.gmall.product.config.properties.MinioProperties
 * @author: hao_bai
 * @date: 2022/8/25 21:30
 * @version: 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix="app.minio.base")
public class MinioProperties {

    private String url;

    private String accessKey;

    private String secretKey;

    private String bucketName;

}
