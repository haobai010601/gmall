package xyz.itmobai.gmall.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import xyz.itmobai.gmall.common.result.Result;
import xyz.itmobai.gmall.product.service.FileUploadService;

/**
 * @classname: xyz.itmobai.gmall.product.controller.FileUploadController
 * @author: hao_bai
 * @date: 2022/8/23 22:18
 * @version: 1.0
 */
@RestController
@RequestMapping("/admin/product")
public class FileUploadController {

    @Autowired
    FileUploadService fileUploadService;

    @PostMapping("/fileUpload")
    public Result fileUpload(@RequestPart("file")MultipartFile file){
        try {
            String fileUrl = fileUploadService.fileUpload(file);
            return Result.ok(fileUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail();
    }

}

