package com.chandrakanthrck.twilio_communication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);  // Number of core threads
        executor.setMaxPoolSize(10);  // Max number of threads
        executor.setQueueCapacity(500);  // Queue size before tasks are rejected
        executor.setThreadNamePrefix("Async-Thread-");
        executor.initialize();
        return executor;
    }
}
