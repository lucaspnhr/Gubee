package com.github.lucaspnhr.persistenceadapter.adapter;

import com.github.lucaspnhr.commom.customexception.NotFoundHeroException;
import com.github.lucaspnhr.outport.LoadHeroPort;
import com.github.lucaspnhr.outport.RetrieveHeroRequest;
import com.github.lucaspnhr.persistenceadapter.entity.HeroJPAEntity;
import com.github.lucaspnhr.persistenceadapter.entity.PowerStatsJPAEntity;
import com.github.lucaspnhr.persistenceadapter.repository.HeroRepository;
import com.github.lucaspnhr.persistenceadapter.repository.PowerStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor

public class HeroPersistenceAdapter implements LoadHeroPort {

    private final HeroRepository heroRepository;
    private final PowerStatsRepository powerStatsRepository;


    @Override
    @Transactional
    public RetrieveHeroRequest loadHeroByid(UUID id) {
        HeroJPAEntity heroFound = heroRepository.findById(id).orElseThrow(() -> new NotFoundHeroException(id));
        PowerStatsJPAEntity powerStats = powerStatsRepository.findById(heroFound.getPowerStatsId()).get();

        return new RetrieveHeroRequest(heroFound.getHeroId(),
                powerStats.getId(),
                heroFound.getName(),
                heroFound.getRace(),
                powerStats.getStrength(),
                powerStats.getAgility(),
                powerStats.getIntelligence(),
                powerStats.getDexterity());
    }
}
