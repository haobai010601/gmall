package xyz.itmobai.gmail.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import xyz.itmobai.gmall.common.result.Result;
import xyz.itmobai.gmall.feign.product.ProductFeignClient;
import xyz.itmobai.gmall.model.to.CategoryTreeTo;

import java.util.List;

/**
 * @classname: xyz.itmobai.gmail.web.controller.IndexController
 * @author: hao_bai
 * @date: 2022/8/26 21:18
 * @version: 1.0
 */
@Controller
public class IndexController {

    @Autowired
    ProductFeignClient productFeignClient;

    @GetMapping("/")
    public String index(Model model){
        Result<List<CategoryTreeTo>> result = productFeignClient.getAllCategoryWithTree();
        if (result.isOk()){
            List<CategoryTreeTo> data = result.getData();
            model.addAttribute("list",data);
        }
        return "index/index";
    }
}
