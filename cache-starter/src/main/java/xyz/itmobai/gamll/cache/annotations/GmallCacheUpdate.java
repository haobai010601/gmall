package xyz.itmobai.gamll.cache.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @classname: xyz.itmobai.gamll.cache.annotations.GmallCache
 * @author: hao_bai
 * @date: 2022/9/1 18:42
 * @version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface GmallCacheUpdate {
    String cacheKey() default ""; //就是cacheKey
}
