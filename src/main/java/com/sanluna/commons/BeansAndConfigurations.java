package com.sanluna.commons;

import com.sanluna.commons.model.HealthChecker;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;


@EnableAutoConfiguration
@Configuration
@ComponentScan({"com.sanluna.commons"})
public class BeansAndConfigurations {

    private HealthChecker healthChecker;

    @PostConstruct
    public void init() {
        this.healthChecker = new HealthChecker();
    }

    @Bean
    public HealthChecker getHealthCheck() {
        return healthChecker;
    }

}
