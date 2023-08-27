package com.maemresen.tcw.sb.remote.docker.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.maemresen.tcw.sb.remote.docker.util.data.loader.BudgetListDataLoader;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class RestIntegrationTestConfig {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Bean
    public BudgetListDataLoader dataLoader() {
        return new BudgetListDataLoader();
    }
}
