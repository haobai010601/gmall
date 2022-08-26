package xyz.itmobai.gmail.web.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.itmobai.gmall.common.result.Result;
import xyz.itmobai.gmall.model.to.CategoryTreeTo;

import java.util.List;

/**
 * @classname: xyz.itmobai.gmail.web.feign.CategoryFeignClient
 * @author: hao_bai
 * @date: 2022/8/26 21:29
 * @version: 1.0
 */

@FeignClient("service-product")
@RequestMapping("/api/inner/rpc/product")
public interface CategoryFeignClient {

    /**
     *1、 给 service-product 发送一个 GET方式的请求 路径是 /api/inner/rpc/product/category/tree
     *2、 拿到远程的响应 json 结果后转成 Result类型的对象，并且 返回的数据是 List<CategoryTreeTo>
     * @return
     */

    @GetMapping("/category/tree")
    Result<List<CategoryTreeTo>> getAllCategoryWithTree();


}
