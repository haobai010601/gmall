package xyz.itmobai.gmall.product.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.itmobai.gmall.common.result.Result;
import xyz.itmobai.gmall.model.product.SpuInfo;
import xyz.itmobai.gmall.product.service.SpuInfoService;

/**
 * @classname: xyz.itmobai.gmall.product.controller.SpuInfoController
 * @author: hao_bai
 * @date: 2022/8/24 0:24
 * @version: 1.0
 */
@RestController
@RequestMapping("/admin/product")
public class SpuInfoController {

    @Autowired
    SpuInfoService spuInfoService;

    @GetMapping("/{page}/{limit}")
    public Result getSpuInfoByCategory3id(@PathVariable("page")Long page,
                                          @PathVariable("limit")Long limit,
                                          @RequestParam("category3Id")Long category3Id){
        QueryWrapper<SpuInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("category3_id", category3Id);
        Page<SpuInfo> spuInfoPage = spuInfoService.page(new Page<SpuInfo>(page, limit), wrapper);
        return Result.ok(spuInfoPage);
    }


}
