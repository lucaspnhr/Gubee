package com.github.lucaspnhr.webadapter.config;

import com.github.lucaspnhr.usecase.CompareHeroUseCase;
import com.github.lucaspnhr.usecase.RegisterHeroUseCase;
import com.github.lucaspnhr.webadapter.controller.CompareHeroesController;
import com.github.lucaspnhr.webadapter.controller.RegisterHeroController;
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
