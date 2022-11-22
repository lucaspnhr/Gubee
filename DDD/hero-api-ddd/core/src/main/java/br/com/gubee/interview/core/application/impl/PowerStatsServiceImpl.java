package br.com.gubee.interview.core.application.impl;

import br.com.gubee.interview.core.application.PowerStatsService;
import br.com.gubee.interview.core.features.powerstats.repository.PowerStatsRepository;
import br.com.gubee.interview.domain.model.powerstats.PowerStats;
import br.com.gubee.interview.domain.model.powerstats.PowerStatsRepository;
import br.com.gubee.interview.domain.model.request.UpdateHeroRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PowerStatsServiceImpl implements PowerStatsService {



    private final PowerStatsRepository powerStatsRepository;

    @Override
    @Transactional
    public UUID create(PowerStats powerStats) {
        return powerStatsRepository.create(powerStats);
    }

    @Override
    @Transactional
    public PowerStats retriveById(UUID id){
        return powerStatsRepository.retriveById(id).get();
    }
    @Override
    @Transactional
    public int update(UpdateHeroRequest updateHeroRequest, UUID powerStatsId) {
        if(updateHeroRequest.hasAllStatsDefault()){
            return 0;
        }
        PowerStats powerStats = retriveById(powerStatsId);
        powerStats.update(updateHeroRequest);
        return powerStatsRepository.update(powerStats);
    }
    @Override
    @Transactional
    public int deleteById(UUID powerStatsId) {
        return powerStatsRepository.delete(powerStatsId);
    }

    @Override
    public void deleteAll() {
        powerStatsRepository.deleteAll();
    }
}
