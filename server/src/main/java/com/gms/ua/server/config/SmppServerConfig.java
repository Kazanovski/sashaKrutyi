package com.gms.ua.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class SmppServerConfig {

    @Value("${wait.bind.service.core.pool.size}")
    private int corePoolSize;

    @Value("${wait.bind.service.max.pool.size}")
    private int maxPoolSize;

    @Value("${wait.bind.service.queue.capacity}")
    private int queueCapacity;

    @Bean(name = "waitBindExecutor")
    public ThreadPoolTaskExecutor waitBindExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(corePoolSize);
        pool.setMaxPoolSize(maxPoolSize);
        pool.setQueueCapacity(queueCapacity);
        pool.setWaitForTasksToCompleteOnShutdown(true);
        return pool;
    }

}
