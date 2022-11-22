package br.com.gubee.interview.core.application.impl;

import br.com.gubee.interview.core.application.PowerStatsService;
import br.com.gubee.interview.core.features.powerstats.repository.PowerStatsRepository;
import br.com.gubee.interview.core.infrastructure.persistence.jdbc.PowerStatsRepositoryStubImpl;
import br.com.gubee.interview.domain.model.powerstats.PowerStats;
import br.com.gubee.interview.domain.model.request.UpdateHeroRequest;
import lombok.AllArgsConstructor;

import java.util.UUID;
@AllArgsConstructor
public class PowerStatsServiceStubImpl implements PowerStatsService {


    private final PowerStatsRepository powerStatsRepository = new PowerStatsRepositoryStubImpl() {
    };


    @Override
    public UUID create(PowerStats powerStats) {
        powerStatsRepository.create(powerStats);
        return powerStats.getId();
    }

    @Override
    public PowerStats retriveById(UUID id) {
        return powerStatsRepository.retriveById(id).get();
    }

    @Override
    public int update(UpdateHeroRequest updateHeroRequest, UUID powerStatsId) {
        if(updateHeroRequest.hasAllStatsDefault()){
            return 0;
        }

        PowerStats powerStats = retriveById(powerStatsId);
        powerStats.update(updateHeroRequest);
        powerStatsRepository.update(powerStats);
        return 1;
    }

    @Override
    public int deleteById(UUID powerStatsId) {
        return powerStatsRepository.delete(powerStatsId);
    }

    @Override
    public void deleteAll() {
        powerStatsRepository.deleteAll();
    }

}