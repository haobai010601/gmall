package xyz.itmobai.gmall.product.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件存储服务接口
 * @classname: xyz.itmobai.gmall.product.service.FileUploadService
 * @author: hao_bai
 * @date: 2022/8/24 0:00
 * @version: 1.0
 */
public interface FileUploadService {
    /**
    * 文件存储服务方法
    */
    String fileUpload(MultipartFile file) throws Exception;

}
