package xyz.itmobai.gmall.model.to;

import lombok.Data;

import java.util.List;

/**
 * @classname: xyz.itmobai.gmall.model.to.CategoryTreeTo
 * @author: hao_bai
 * @date: 2022/8/26 21:31
 * @version: 1.0
 */
@Data
public class CategoryTreeTo {

    private Long categoryId;

    private String categoryName;

    private List<CategoryTreeTo> categoryChild;

}
