package com.github.lucaspnhr.configuration;

import com.github.lucaspnhr.outport.LoadHeroPort;
import com.github.lucaspnhr.outport.SaveHeroPort;
import com.github.lucaspnhr.persistenceadapter.config.JpaConfig;
import com.github.lucaspnhr.service.CompareHeroService;
import com.github.lucaspnhr.service.RegisterHeroService;
import com.github.lucaspnhr.usecase.CompareHeroUseCase;
import com.github.lucaspnhr.usecase.RegisterHeroUseCase;
import com.github.lucaspnhr.webadapter.config.WebConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import({JpaConfig.class, WebConfig.class})
public class SpringContextConfiguration {

    @Autowired
    private LoadHeroPort loadHeroPort;
    @Autowired
    private SaveHeroPort saveHeroPort;

    @Bean
    public CompareHeroUseCase compareHeroUseCase(){
        return new CompareHeroService(loadHeroPort);
    }

    @Bean
    public RegisterHeroUseCase registerHeroUseCase(){
        return new RegisterHeroService(saveHeroPort);
    }


}
