package xyz.itmobai.gmall.product.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.itmobai.gmall.common.result.Result;
import xyz.itmobai.gmall.model.product.BaseTrademark;
import xyz.itmobai.gmall.product.service.BaseTrademarkService;

/**
 * @classname: xyz.itmobai.gmall.product.controller.TrademarkController
 * @author: hao_bai
 * @date: 2022/8/23 21:46
 * @version: 1.0
 */
@RequestMapping("/admin/product")
@RestController
public class TrademarkController {

    @Autowired
    BaseTrademarkService baseTrademarkService;

    @GetMapping("/baseTrademark/{page}/{limit}")
    public Result getBaseTrademarkPage(@PathVariable("page")Long page,
                                       @PathVariable("limit")Long limit){
        Page<BaseTrademark> trademarkPage = baseTrademarkService.page(new Page<>(page, limit));
        return Result.ok(trademarkPage);
    }

    @GetMapping("/baseTrademark/get/{id}")
    public Result getBaseTrademarkById(@PathVariable("id")Long id){
        BaseTrademark trademark = baseTrademarkService.getById(id);
        return Result.ok(trademark);
    }

    @PostMapping("/baseTrademark/save")
    public Result saveBaseTrademark(@RequestBody BaseTrademark baseTrademark){
        baseTrademarkService.save(baseTrademark);
        return Result.ok();
    }

    @PutMapping("/baseTrademark/update")
    public Result updateBaseTrademark(@RequestBody BaseTrademark baseTrademark){
        baseTrademarkService.updateById(baseTrademark);
        return Result.ok();
    }

    @DeleteMapping("/baseTrademark/remove/{id}")
    public Result deleteBaseTrademark(@PathVariable("id")Long id){
        baseTrademarkService.removeById(id);
        return Result.ok();
    }

}
