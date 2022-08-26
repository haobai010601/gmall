package xyz.itmobai.gmall.product.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.itmobai.gmall.common.result.Result;
import xyz.itmobai.gmall.model.to.CategoryTreeTo;
import xyz.itmobai.gmall.product.service.BaseCategory1Service;

import java.util.List;

/**
 * @classname: xyz.itmobai.gmall.product.controller.CategoryApi
 * @author: hao_bai
 * @date: 2022/8/22 19:03
 * @version: 1.0
 */

@RestController
@RequestMapping("/api/inner/rpc/product/category")
public class CategoryApi {

    @Autowired
    BaseCategory1Service baseCategory1Service;

    @GetMapping("/tree")
    public Result<List<CategoryTreeTo>> getAllCategoryWithTree(){
        List<CategoryTreeTo> list = baseCategory1Service.getAllCategoryWithTree();
        return Result.ok(list);
    };
}
