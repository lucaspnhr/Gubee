package br.com.gubee.interview.core.features.powerstats;

import br.com.gubee.interview.model.PowerStats;
import br.com.gubee.interview.model.request.UpdateHeroRequest;
import lombok.AllArgsConstructor;

import java.util.UUID;
@AllArgsConstructor
public class PowerStatsServiceStubImpl implements PowerStatsService{


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
        PowerStats toBeUpdate = retriveById(powerStatsId);
        toBeUpdate.setAgility(updateHeroRequest.getAgility());
        toBeUpdate.setStrength(updateHeroRequest.getStrength());
        toBeUpdate.setIntelligence(updateHeroRequest.getIntelligence());
        toBeUpdate.setDexterity(updateHeroRequest.getDexterity());
        powerStatsRepository.update(toBeUpdate);
        return 1;
    }

    @Override
    public int deleteById(UUID powerStatsId) {
        return powerStatsRepository.delete(powerStatsId);
    }

}