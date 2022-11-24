package com.github.lucaspnhr.webadapter.config;

import com.github.lucaspnhr.usecase.CompareHeroUseCase;
import com.github.lucaspnhr.webadapter.controller.CompareHeroesController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {


    @Bean
    public CompareHeroesController compareHeroesController(CompareHeroUseCase compareHeroUseCase){
        return new CompareHeroesController(compareHeroUseCase);
    }
}
