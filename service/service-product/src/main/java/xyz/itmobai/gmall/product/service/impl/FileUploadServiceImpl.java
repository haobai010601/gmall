package xyz.itmobai.gmall.product.service.impl;

import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.itmobai.gmall.common.util.DateUtil;
import xyz.itmobai.gmall.product.config.properties.MinioProperties;
import xyz.itmobai.gmall.product.service.FileUploadService;

import java.util.Date;
import java.util.UUID;

/**
 * @classname: xyz.itmobai.gmall.product.service.impl.FileUploadServiceImpl
 * @author: hao_bai
 * @date: 2022/8/24 0:01
 * @version: 1.0
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    MinioClient minioClient;
    @Autowired
    MinioProperties minioProperties;

    @Override
    public String fileUpload(MultipartFile file) throws Exception {
        // 检查存储桶是否已经存在
        boolean isExist = minioClient.bucketExists(minioProperties.getBucketName());
        // 如果存储桶不存在，则创建
        if(!isExist) {
            minioClient.makeBucket(minioProperties.getBucketName());
        }
        //获取一个随机文件名
        String randomFileName = getRandomFileName(file);
        //获取当天时间
        String date = DateUtil.formatDate(new Date());
        //拼接文件在bucket中的存储路径
        String fileInBucketUrl = date + "/" + randomFileName;
        //创建文件上传参数
        PutObjectOptions options = new PutObjectOptions(file.getSize(), -1);
        //设置文件上传类型
        options.setContentType("image/png");

        //向minio中上传文件
        minioClient.putObject(minioProperties.getBucketName(),
                fileInBucketUrl,
                file.getInputStream(),
                options
                );

        //拼接文件访问路径
        String fileUrl = minioProperties.getUrl() + "/" + minioProperties.getBucketName() + "/" + fileInBucketUrl;
        return fileUrl;
    }

    private String getRandomFileName(MultipartFile file) {
        //获取上传的文件名称
        String fileName = file.getOriginalFilename();
        //获取文件格式后缀
        String fileFormat = fileName.substring(fileName.lastIndexOf("."));
        //获取一个随机字符串用作文件名并拼接后缀
        String randomFileName = UUID.randomUUID().toString().replace("-", "").substring(0,10) + fileFormat;
        return randomFileName;
    }
}
