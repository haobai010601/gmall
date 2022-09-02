package xyz.itmobai.gmall.product.bloom;

/**
 * @classname: xyz.itmobai.gmall.product.bloom.BloomOpsService
 * @author: hao_bai
 * @date: 2022/9/1 20:59
 * @version: 1.0
 */
public interface BloomOpsService {

    /**
     * 重建指定布隆过滤器
     * @param bloomName
     */
    void rebuildBloom(String bloomName,BloomDataQueryService dataQueryService);
}
