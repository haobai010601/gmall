package xyz.itmobai.gmall.product.service.impl;

import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.itmobai.gmall.common.util.DateUtil;
import xyz.itmobai.gmall.product.config.MinioConfig;
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

    private static final String TRADEMARK_LOGO_BUCKET = "trademark-logo";

    @Autowired
    MinioConfig minioConfig;

    @Override
    public String fileUpload(MultipartFile file) throws Exception {
        // 使用MinIO服务的URL，端口，Access key和Secret key创建一个MinioClient对象
        MinioClient minioClient = new MinioClient(minioConfig.getUrl(),minioConfig.getAccessKey(), minioConfig.getSecretKey());
        // 检查存储桶是否已经存在
        boolean isExist = minioClient.bucketExists(TRADEMARK_LOGO_BUCKET);
        if(!isExist) {
            return "上传文件出错，请联系管理员查询是否有logo存储桶";
        }
        //获取一个随机文件名
        String randomFileName = getRandomFileName(file);
        //获取当天时间
        String date = DateUtil.formatDate(new Date());
        //拼接文件在bucket中的存储路径
        String fileInBucketUrl = date + "/" + randomFileName;
        //向minio中上传文件
        minioClient.putObject(TRADEMARK_LOGO_BUCKET,fileInBucketUrl,file.getInputStream(),new PutObjectOptions(file.getSize(),1024*1024*5));
        //拼接文件访问路径
        String fileUrl = minioConfig.getUrl() + "/" + TRADEMARK_LOGO_BUCKET + "/" + fileInBucketUrl;
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
