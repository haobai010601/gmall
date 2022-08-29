package xyz.itmobai.gmall.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.itmobai.gmall.common.util.Jsons;
import xyz.itmobai.gmall.model.product.SpuSaleAttr;
import xyz.itmobai.gmall.model.to.ValueSkuJsonTo;
import xyz.itmobai.gmall.product.mapper.SpuSaleAttrMapper;
import xyz.itmobai.gmall.product.service.SpuSaleAttrService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author Hi
* @description 针对表【spu_sale_attr(spu销售属性)】的数据库操作Service实现
* @createDate 2022-08-24 00:17:27
*/
@Service
public class SpuSaleAttrServiceImpl extends ServiceImpl<SpuSaleAttrMapper, SpuSaleAttr>
    implements SpuSaleAttrService{

    @Autowired
    SpuSaleAttrMapper spuSaleAttrMapper;

    @Override
    public List<SpuSaleAttr> getSpuSaleAttrListBySpuId(Long spuId) {
        List<SpuSaleAttr> list = spuSaleAttrMapper.getSpuSaleAttrListBySpuId(spuId);
        return list;
    }

    @Override
    public List<SpuSaleAttr> getSaleAttrAndValueMarkSku(Long spuId, Long skuId) {
        return spuSaleAttrMapper.getSaleAttrAndValueMarkSku(spuId,skuId);
    }

    @Override
    public String getAllSkuSaleAttrValueJson(Long spuId) {
        List<ValueSkuJsonTo> list = spuSaleAttrMapper.getAllSkuSaleAttrValueJson(spuId);
        Map<String,Long> map = new HashMap<>();
        list.forEach((valueSkuJsonTo)->{
            String valueJson = valueSkuJsonTo.getValueJson();
            Long skuId = valueSkuJsonTo.getSkuId();
            map.put(valueJson,skuId);
        });
        return Jsons.toStr(map);
    }
}




