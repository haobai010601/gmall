package xyz.itmobai.gmall.product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.itmobai.gmall.common.result.Result;
import xyz.itmobai.gmall.model.product.BaseAttrValue;
import xyz.itmobai.gmall.product.service.BaseAttrValueService;

import java.util.List;

/**
 * @classname: xyz.itmobai.gmall.product.controller.AttrValueController
 * @author: hao_bai
 * @date: 2022/8/23 0:09
 * @version: 1.0
 */
@RestController
@RequestMapping("/admin/product")
public class AttrValueController {

    @Autowired
    BaseAttrValueService baseAttrValueService;

    @GetMapping("/getAttrValueList/{attrId}")
    public Result getAttrValueList(@PathVariable("attrId")Long attrId){
        List<BaseAttrValue> list = baseAttrValueService.getAttrValueList(attrId);
        return Result.ok(list);
    }

}
