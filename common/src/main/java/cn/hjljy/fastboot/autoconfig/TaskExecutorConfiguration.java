package cn.hjljy.fastboot.autoconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author yichaofan
 * @date 2020/6/4 9:58
 * @apiNote 线程池配置
 */
@Configuration
@EnableAsync
public class TaskExecutorConfiguration {

    @Bean
    @Primary
    public ThreadPoolTaskExecutor taskExecutor() {

        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        // 设置线程池核心线程
        taskExecutor.setCorePoolSize(8);
        // 设置线程池最大线程
        taskExecutor.setMaxPoolSize(16);
        // 设置等待队列
        taskExecutor.setQueueCapacity(40);
        // 设置线程存活时间
        taskExecutor.setKeepAliveSeconds(100);
        // 设置线程名称
        taskExecutor.setThreadNamePrefix("taskExecutor-");
        // 设置线程拒绝策略为由调用者线程执行
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 设置线程池如果没有任务正在执行就关闭
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        // 设置关闭等待时间为一分钟
        taskExecutor.setAwaitTerminationMillis(60 * 1000);
        // 初始化线程池
        taskExecutor.initialize();
        return taskExecutor;
    }
}
