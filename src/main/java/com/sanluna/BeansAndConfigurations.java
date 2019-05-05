package com.sanluna;

import com.sanluna.commons.model.HealthChecker;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;


@EnableAutoConfiguration
@Configuration
@ComponentScan({"com.sanluna.commons", "com.sanluna.security"})
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

    @Bean
    @Primary
    public BCryptPasswordEncoder userPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
