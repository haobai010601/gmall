package xyz.itmobai.gmail.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import xyz.itmobai.gmail.web.feign.SkuDetailFeignClient;
import xyz.itmobai.gmall.common.result.Result;
import xyz.itmobai.gmall.model.to.SkuDetailTo;

/**
 * @classname: xyz.itmobai.gmail.web.controller.ItemController
 * @author: hao_bai
 * @date: 2022/8/26 22:02
 * @version: 1.0
 */
@Controller
public class ItemController {

    @Autowired
    SkuDetailFeignClient skuDetailFeignClient;

    @GetMapping("/{skuId}.html")
    public String item(@PathVariable("skuId") Long skuId,
                       Model model){
        Result<SkuDetailTo> result = skuDetailFeignClient.getSkuDetail(skuId);
        if (result.isOk()){
            SkuDetailTo data = result.getData();
            model.addAttribute("skuInfo",data.getSkuInfo());
            model.addAttribute("price",data.getPrice());
            model.addAttribute("categoryView",data.getCategoryViewTo());
            model.addAttribute("spuSaleAttrList",data.getSpuSaleAttrList());
            model.addAttribute("skuImage",data.getSkuInfo().getSkuImageList());
        }
        return "item/index";
    }
}
