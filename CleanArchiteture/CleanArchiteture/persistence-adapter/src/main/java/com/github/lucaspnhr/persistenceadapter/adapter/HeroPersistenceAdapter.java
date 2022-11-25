package com.github.lucaspnhr.persistenceadapter.adapter;

import com.github.lucaspnhr.commom.customexception.NotFoundHeroException;
import com.github.lucaspnhr.outport.LoadHeroPort;
import com.github.lucaspnhr.outport.RetrievedHero;
import com.github.lucaspnhr.outport.SaveHeroCommand;
import com.github.lucaspnhr.outport.SaveHeroPort;
import com.github.lucaspnhr.persistenceadapter.entity.HeroJPAEntity;
import com.github.lucaspnhr.persistenceadapter.entity.PowerStatsJPAEntity;
import com.github.lucaspnhr.persistenceadapter.repository.HeroRepository;
import com.github.lucaspnhr.persistenceadapter.repository.PowerStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor

public class HeroPersistenceAdapter implements LoadHeroPort, SaveHeroPort {

    private final HeroRepository heroRepository;
    private final PowerStatsRepository powerStatsRepository;


    @Override
    @Transactional
    public RetrievedHero loadHeroByid(UUID id) {
        HeroJPAEntity heroFound = heroRepository.findById(id).orElseThrow(() -> new NotFoundHeroException(id));
        PowerStatsJPAEntity powerStats = powerStatsRepository.findById(heroFound.getPowerStatsId()).get();

        return new RetrievedHero(heroFound.getHeroId(),
                powerStats.getId(),
                heroFound.getName(),
                heroFound.getRace(),
                powerStats.getStrength(),
                powerStats.getAgility(),
                powerStats.getIntelligence(),
                powerStats.getDexterity());
    }

    @Override
    @Transactional
    public boolean saveHero(SaveHeroCommand saveHeroCommand) {
        HeroJPAEntity hero = new HeroJPAEntity(saveHeroCommand);
        PowerStatsJPAEntity powerStatsJPA = new PowerStatsJPAEntity(saveHeroCommand);

        heroRepository.save(hero);
        powerStatsRepository.save(powerStatsJPA);

        return true;
    }
}
