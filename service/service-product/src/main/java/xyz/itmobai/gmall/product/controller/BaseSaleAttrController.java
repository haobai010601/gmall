package xyz.itmobai.gmall.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.itmobai.gmall.common.result.Result;
import xyz.itmobai.gmall.model.product.BaseSaleAttr;
import xyz.itmobai.gmall.product.service.BaseSaleAttrService;

import java.util.List;

/**
 * @classname: xyz.itmobai.gmall.product.controller.BaseSaleAttrController
 * @author: hao_bai
 * @date: 2022/8/24 1:44
 * @version: 1.0
 */
@RequestMapping("/admin/product")
@RestController
public class BaseSaleAttrController {

    @Autowired
    BaseSaleAttrService baseSaleAttrService;

    @GetMapping("/baseSaleAttrList")
    public Result getSaleAttrList(){
        List<BaseSaleAttr> list = baseSaleAttrService.list();
        return Result.ok(list);
    }
}
