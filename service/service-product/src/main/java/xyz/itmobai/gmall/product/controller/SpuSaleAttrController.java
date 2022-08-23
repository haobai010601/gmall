package xyz.itmobai.gmall.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.itmobai.gmall.common.result.Result;
import xyz.itmobai.gmall.model.product.SpuSaleAttr;
import xyz.itmobai.gmall.product.service.SpuSaleAttrService;

import java.util.List;

/**
 * @classname: xyz.itmobai.gmall.product.controller.SpuSaleAttrController
 * @author: hao_bai
 * @date: 2022/8/24 0:39
 * @version: 1.0
 */
@RestController
@RequestMapping("/admin/product")
public class SpuSaleAttrController {

    @Autowired
    SpuSaleAttrService spuSaleAttrService;

    @GetMapping("/spuSaleAttrList/{spuId}")
    public Result getSpuSaleAttrListBySpuId(@PathVariable("spuId")Long spuId){
        List<SpuSaleAttr> list = spuSaleAttrService.getSpuSaleAttrListBySpuId(spuId);
        return Result.ok(list);
    }
}
