package com.github.lucaspnhr.restadapter.config;

import com.github.lucaspnhr.restadapter.controller.CompareHeroesController;
import com.github.lucaspnhr.restadapter.controller.RegisterHeroController;
import com.github.lucaspnhr.usecase.CompareHeroUseCase;
import com.github.lucaspnhr.usecase.RegisterHeroUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {


    @Bean
    public CompareHeroesController compareHeroesController(CompareHeroUseCase compareHeroUseCase){
        return new CompareHeroesController(compareHeroUseCase);
    }

    @Bean
    public RegisterHeroController registerheroController(RegisterHeroUseCase registerHeroUseCase){
        return new RegisterHeroController(registerHeroUseCase);
    }
}
