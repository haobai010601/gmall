package xyz.itmobai.gmall.product.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.itmobai.gmall.common.result.Result;
import xyz.itmobai.gmall.model.product.SkuInfo;
import xyz.itmobai.gmall.product.service.SkuInfoService;

/**
 * @classname: xyz.itmobai.gmall.product.controller.SkuInfoController
 * @author: hao_bai
 * @date: 2022/8/24 0:53
 * @version: 1.0
 */
@RestController
@RequestMapping("/admin/product")
public class SkuInfoController {

    @Autowired
    SkuInfoService skuInfoService;

    @GetMapping("/list/{page}/{limit}")
    public Result getSkuInfoList(@PathVariable("page")Long page,
                                 @PathVariable("limit")Long limit){
        Page<SkuInfo> skuInfoPage = skuInfoService.page(new Page<SkuInfo>(page, limit));
        return Result.ok(skuInfoPage);
    }

    @GetMapping("/onSale/{skuId}")
    public Result onSale(@PathVariable("skuId")Long skuId){
        skuInfoService.onSale(skuId);
        return Result.ok();
    }

    @GetMapping("/cancelSale/{skuId}")
    public Result cancelSale(@PathVariable("skuId")Long skuId){
        skuInfoService.cancelSale(skuId);
        return Result.ok();
    }
}
