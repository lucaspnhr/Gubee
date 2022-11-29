package com.github.lucaspnhr.configuration;

import com.github.lucaspnhr.outport.LoadHeroPort;
import com.github.lucaspnhr.outport.SaveHeroPort;
import com.github.lucaspnhr.persistenceadapter.config.JpaConfig;
import com.github.lucaspnhr.service.CompareHeroService;
import com.github.lucaspnhr.service.RegisterHeroService;
import com.github.lucaspnhr.usecase.CompareHeroUseCase;
import com.github.lucaspnhr.usecase.RegisterHeroUseCase;
import com.github.lucaspnhr.webadapter.config.WebConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
@Configuration
@Import({JpaConfig.class, WebConfig.class})
public class SpringContextConfiguration {

    private final LoadHeroPort loadHeroPort;
    private final SaveHeroPort saveHeroPort;

    public SpringContextConfiguration(LoadHeroPort loadHeroPort, SaveHeroPort saveHeroPort) {
        this.loadHeroPort = loadHeroPort;
        this.saveHeroPort = saveHeroPort;
    }

    @Bean
    public CompareHeroUseCase compareHeroUseCase(){
        return new CompareHeroService(loadHeroPort);
    }

    @Bean
    public RegisterHeroUseCase registerHeroUseCase(){
        return new RegisterHeroService(saveHeroPort);
    }


}
