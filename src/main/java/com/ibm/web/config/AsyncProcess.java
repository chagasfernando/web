package com.ibm.web.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncProcess extends AsyncConfigurerSupport{
	@Override
    public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	    executor.setCorePoolSize(2);
	    executor.setMaxPoolSize(5);
	    executor.setQueueCapacity(30);
	    executor.setThreadNamePrefix("MyApp");
	    executor.initialize();
	    return executor;		
	}
}

