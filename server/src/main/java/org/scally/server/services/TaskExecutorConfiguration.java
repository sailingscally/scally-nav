package org.scally.server.services;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class TaskExecutorConfiguration {

  @Bean( name = "executor" )
  public Executor getExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setThreadNamePrefix("executor-");
    executor.setDaemon( false );
    executor.setCorePoolSize( 20 );
    executor.setMaxPoolSize( 20 );
    executor.setQueueCapacity( 10 );
    executor.setKeepAliveSeconds( 60 );
    executor.initialize();

    return executor;
  }
}
