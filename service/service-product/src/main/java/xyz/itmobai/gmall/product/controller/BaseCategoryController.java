package xyz.itmobai.gmall.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.itmobai.gmall.common.result.Result;
import xyz.itmobai.gmall.model.product.BaseCategory1;
import xyz.itmobai.gmall.model.product.BaseCategory2;
import xyz.itmobai.gmall.model.product.BaseCategory3;
import xyz.itmobai.gmall.product.service.BaseCategory1Service;
import xyz.itmobai.gmall.product.service.BaseCategory2Service;
import xyz.itmobai.gmall.product.service.BaseCategory3Service;

import java.util.List;

/**
 * @classname: xyz.itmobai.gmall.product.controller.BaseCategoryController
 * @author: hao_bai
 * @date: 2022/8/22 19:03
 * @version: 1.0
 */
@RequestMapping("/admin/product")
@RestController
public class BaseCategoryController {

    @Autowired
    BaseCategory1Service baseCategory1Service;
    @Autowired
    BaseCategory2Service baseCategory2Service;
    @Autowired
    BaseCategory3Service baseCategory3Service;

    @GetMapping("/getCategory1")
    public Result getCategory1(){
        List<BaseCategory1> list = baseCategory1Service.list();
        return Result.ok(list);
    }

    @GetMapping("/getCategory2/{category1Id}")
    public Result getCategory2(@PathVariable("category1Id")Long category1Id){
        List<BaseCategory2> list = baseCategory2Service.getCategoryByFatherId(category1Id);
        return Result.ok(list);
    }

    @GetMapping("/getCategory3/{category2Id}")
    public Result getCategory3(@PathVariable("category2Id")Long category2Id){
        List<BaseCategory3> list = baseCategory3Service.getCategoryByFatherId(category2Id);
        return Result.ok(list);
    }
}
