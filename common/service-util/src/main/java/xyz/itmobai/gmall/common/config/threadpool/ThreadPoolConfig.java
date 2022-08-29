package xyz.itmobai.gmall.common.config.threadpool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @classname: xyz.itmobai.gmall.common.config.threadpool.ThreadPoolConfig
 * @author: hao_bai
 * @date: 2022/8/29 23:42
 * @version: 1.0
 */
@Configuration
@EnableConfigurationProperties(ThreadPoolProperties.class)
public class ThreadPoolConfig {

    @Autowired
    ThreadPoolProperties threadPoolProperties;

    @Value("${spring.application.name}")
    String applicationName;

    @Bean
    public ThreadPoolExecutor getThreadPoolExecutor(){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                threadPoolProperties.getCore(),
                threadPoolProperties.getCore(),
                threadPoolProperties.getKeepAliveTime(),
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(threadPoolProperties.getQueueSize()),
                new ThreadFactory() { //负责给线程池创建线程
                    int i = 0; //记录线程自增id
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r); //创建的线程要接受任务
                        thread.setName(applicationName+"[core-thread-"+ i++ +"]");
                        return thread;
                    }
                },
                //生产环境用 CallerRuns，保证就算线程池满了，
                // 不能提交的任务，由当前线程自己以同步的方式执行
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        return threadPoolExecutor;
    }
}
