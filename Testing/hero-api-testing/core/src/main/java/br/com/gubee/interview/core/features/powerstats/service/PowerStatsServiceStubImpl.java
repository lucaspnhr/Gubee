package br.com.gubee.interview.core.features.powerstats.service;

import br.com.gubee.interview.core.features.powerstats.repository.PowerStatsRepository;
import br.com.gubee.interview.core.features.powerstats.service.PowerStatsService;
import br.com.gubee.interview.model.PowerStats;
import br.com.gubee.interview.model.request.UpdateHeroRequest;
import lombok.AllArgsConstructor;

import java.util.UUID;
@AllArgsConstructor
public class PowerStatsServiceStubImpl implements PowerStatsService {


    private final PowerStatsRepository powerStatsRepository;


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
        if(updateHeroRequest.getDexterity() == null &&
                updateHeroRequest.getStrength() == null &&
                updateHeroRequest.getIntelligence() == null &&
                updateHeroRequest.getDexterity() == null){
            return 0;
        }

        PowerStats powerStats = retriveById(powerStatsId);
        if (updateHeroRequest.getDexterity() != null){
            powerStats.setDexterity(updateHeroRequest.getDexterity());
        }else if(updateHeroRequest.getStrength() != null){
            powerStats.setStrength(updateHeroRequest.getStrength());
        } else if (updateHeroRequest.getIntelligence() != null) {
            powerStats.setIntelligence(updateHeroRequest.getIntelligence());
        } else if (updateHeroRequest.getAgility() != null) {
            powerStats.setAgility(updateHeroRequest.getAgility());
        }
        powerStatsRepository.update(powerStats);
        return 1;
    }

    @Override
    public int deleteById(UUID powerStatsId) {
        return powerStatsRepository.delete(powerStatsId);
    }

}