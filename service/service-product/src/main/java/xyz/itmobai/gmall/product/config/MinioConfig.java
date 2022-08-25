package xyz.itmobai.gmall.product.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.itmobai.gmall.product.config.properties.MinioProperties;

/**
 * @classname: xyz.itmobai.gmall.product.config.MinioConfig
 * @author: hao_bai
 * @date: 2022/8/23 23:21
 * @version: 1.0
 */
@Configuration
public class MinioConfig {

    @Autowired
    MinioProperties minioProperties;

    @Bean
    public MinioClient getMinioClient() throws Exception{
        // 使用MinIO服务的URL，端口，Access key和Secret key创建一个MinioClient对象
        MinioClient minioClient = new MinioClient(minioProperties.getUrl(),
                minioProperties.getAccessKey(),
                minioProperties.getSecretKey());
        return minioClient;
    }



}
