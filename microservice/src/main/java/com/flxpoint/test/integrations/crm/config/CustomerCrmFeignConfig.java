package com.flxpoint.test.integrations.crm.config;

import feign.Feign;
import feign.Request;
import me.bvn13.openfeign.logger.normalized.NormalizedFeignLogger;
import org.springframework.context.annotation.Bean;

import feign.Logger;

import java.util.concurrent.TimeUnit;

public class CustomerCrmFeignConfig {
    @Bean
    Logger feignLoggerLevel() {
        return new NormalizedFeignLogger();
    }

    @Bean
    public Feign.Builder feignBuilder() {
        return Feign.builder()
                .options(new Request.Options(2, TimeUnit.SECONDS, 5, TimeUnit.SECONDS, true));
    }
}
