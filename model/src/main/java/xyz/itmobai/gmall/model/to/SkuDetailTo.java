package xyz.itmobai.gmall.model.to;

import lombok.Data;
import xyz.itmobai.gmall.model.product.SkuInfo;
import xyz.itmobai.gmall.model.product.SpuSaleAttr;

import java.math.BigDecimal;
import java.util.List;

/**
 * @classname: xyz.itmobai.gmall.model.to.SkuDetailTo
 * @author: hao_bai
 * @date: 2022/8/27 0:03
 * @version: 1.0
 */
@Data
public class SkuDetailTo {
    private CategoryViewTo categoryViewTo;
    private SkuInfo skuInfo;
    private BigDecimal price;
    private List<SpuSaleAttr> spuSaleAttrList;
    private String valuesSkuJson;
}
