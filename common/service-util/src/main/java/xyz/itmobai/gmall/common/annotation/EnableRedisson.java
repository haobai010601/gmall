package xyz.itmobai.gmall.common.annotation;


import org.springframework.context.annotation.Import;
import xyz.itmobai.gmall.common.config.RedissonConfig;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(RedissonConfig.class)
//1、导入 AppThreadPoolAutoConfiguration 组件。
//2、开启 @EnableConfigurationProperties(AppThreadPoolProperties.class) 这个配置
//     - 和配置文件绑好
//     - AppThreadPoolProperties 放到容器
//3、AppThreadPoolAutoConfiguration 给容器中放一个 ThreadPoolExecutor
//效果： 随时 @Autowired ThreadPoolExecutor即可，也很方便改配置
public @interface EnableRedisson {
}
