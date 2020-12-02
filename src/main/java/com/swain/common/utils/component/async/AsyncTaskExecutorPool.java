package com.swain.common.utils.component.async;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
@PropertySource(value = {"classpath:application.properties"})
public class AsyncTaskExecutorPool {
    @Value("${async.task.core-pool-size}")
    private Integer corePoolSize;

    @Value("${async.task.max-pool-size}")
    private Integer maxPoolSize;

    @Value("${async.task.queue-capacity}")
    private Integer queueCapacity;

    @Value("${async.task.keep-alive-seconds}")
    private Integer keepAliveSeconds;

    @Bean("AsyncTask")
    public Executor timeTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setThreadNamePrefix("AsyncTask-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

}
