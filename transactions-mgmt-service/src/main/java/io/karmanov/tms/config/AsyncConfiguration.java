package io.karmanov.tms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfiguration {


    @Value("${watermark.thread-pool.core.size:10}")
    private int threadPoolCoreSize;

    @Value("${watermark.thread-pool.queue-capacity:500}")
    private int threadPoolQueueCapacity;

    @Value("${watermark.thread-pool.max.size:15}")
    private int threadPoolMaxSize;

    @Bean(name = "threadPoolTaskExecutor")
    public TaskExecutor getTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(threadPoolCoreSize);
        threadPoolTaskExecutor.setMaxPoolSize(threadPoolMaxSize);
        threadPoolTaskExecutor.setQueueCapacity(threadPoolQueueCapacity);
        return threadPoolTaskExecutor;
    }

}
