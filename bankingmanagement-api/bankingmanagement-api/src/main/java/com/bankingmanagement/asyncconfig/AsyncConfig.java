package com.bankingmanagement.asyncconfig;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync // Enables Spring's asynchronous method execution capability.

public class AsyncConfig {
	@Bean("asyncBean")
	public Executor asyncConfig() {

		ThreadPoolTaskExecutor threadPoolExecutor = new ThreadPoolTaskExecutor();

		threadPoolExecutor.setCorePoolSize(5); // Number of working threads at the application load.
		threadPoolExecutor.setMaxPoolSize(50); // Maximum number of worker threads it can create depending upon the
												// tasks to be executed.
		threadPoolExecutor.setQueueCapacity(100); // Worker threads put the tasks in this queue from where the pick it
													// up asynchronously for their executions.
		threadPoolExecutor.setThreadNamePrefix("bugtrackingasync-");//Set name of the thread.
		threadPoolExecutor.initialize();

		return threadPoolExecutor;
	}
}