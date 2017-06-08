package com.challenge.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncTaskExecutorConfiguration {

    @Value("${challenge.thread-pool.core.size:10}")
    private int threadPoolCoreSize;

    @Value("${challenge.thread-pool.queue-capacity:500}")
    private int threadPoolQueueCapacity;

    @Value("${challenge.thread-pool.max.size:15}")
    private int threadPoolMaxSize;

    @Bean(name = "threadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(threadPoolCoreSize);
        threadPoolTaskExecutor.setMaxPoolSize(threadPoolMaxSize);
        threadPoolTaskExecutor.setQueueCapacity(threadPoolQueueCapacity);
        return threadPoolTaskExecutor;
    }
}
