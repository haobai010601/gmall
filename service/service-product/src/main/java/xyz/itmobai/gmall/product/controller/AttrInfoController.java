package xyz.itmobai.gmall.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.itmobai.gmall.common.result.Result;
import xyz.itmobai.gmall.model.product.BaseAttrInfo;
import xyz.itmobai.gmall.product.service.BaseAttrInfoService;

import java.util.List;

/**
 * @classname: xyz.itmobai.gmall.product.controller.AttrInfoController
 * @author: hao_bai
 * @date: 2022/8/22 22:20
 * @version: 1.0
 */
@RestController
@RequestMapping("/admin/product")
public class AttrInfoController {

    @Autowired
    BaseAttrInfoService baseAttrInfoService;



    @GetMapping("/attrInfoList/{category1Id}/{category2Id}/{category3Id}")
    public Result getAttrInfoList(@PathVariable("category1Id") Long category1Id,
                                  @PathVariable("category2Id") Long category2Id,
                                  @PathVariable("category3Id") Long category3Id){
        List<BaseAttrInfo> list = baseAttrInfoService.getAttrInfoList(category1Id,category2Id,category3Id);
        return Result.ok(list);
    }

    @PostMapping("/saveAttrInfo")
    public Result saveAttrInfo(@RequestBody BaseAttrInfo baseAttrInfo){
        baseAttrInfoService.saveAttrInfo(baseAttrInfo);
        return Result.ok();
    }

}
