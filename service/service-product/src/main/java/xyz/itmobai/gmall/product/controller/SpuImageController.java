package xyz.itmobai.gmall.product.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.itmobai.gmall.common.result.Result;
import xyz.itmobai.gmall.model.product.SpuImage;
import xyz.itmobai.gmall.product.service.SpuImageService;

import java.util.List;

/**
 * @classname: xyz.itmobai.gmall.product.controller.SpuImageController
 * @author: hao_bai
 * @date: 2022/8/24 1:14
 * @version: 1.0
 */
@RestController
@RequestMapping("/admin/product")
public class SpuImageController {

    @Autowired
    SpuImageService spuImageService;

    @GetMapping("/spuImageList/{spuId}")
    public Result getSpuImageList(@PathVariable("spuId")Long spuId){
        QueryWrapper<SpuImage> wrapper = new QueryWrapper<>();
        wrapper.eq("spu_id",spuId);
        List<SpuImage> spuImages = spuImageService.list(wrapper);
        return Result.ok(spuImages);
    }



}
