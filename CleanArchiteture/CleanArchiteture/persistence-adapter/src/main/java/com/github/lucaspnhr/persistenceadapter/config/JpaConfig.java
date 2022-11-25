package com.github.lucaspnhr.persistenceadapter.config;

import com.github.lucaspnhr.outport.LoadHeroPort;
import com.github.lucaspnhr.outport.SaveHeroPort;
import com.github.lucaspnhr.persistenceadapter.adapter.HeroPersistenceAdapter;
import com.github.lucaspnhr.persistenceadapter.repository.HeroRepository;
import com.github.lucaspnhr.persistenceadapter.repository.PowerStatsRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = {HeroRepository.class, PowerStatsRepository.class})
public class JpaConfig {

    @Bean
    public LoadHeroPort loadHeroPort(HeroRepository heroRepository, PowerStatsRepository powerStatsRepository){
        return new HeroPersistenceAdapter(heroRepository, powerStatsRepository);
    }

    @Bean
    public SaveHeroPort saveHeroPort(HeroRepository heroRepository, PowerStatsRepository powerStatsRepository){
        return new HeroPersistenceAdapter(heroRepository, powerStatsRepository);
    }
}


