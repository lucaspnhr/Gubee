package br.com.gubee.interview.core.features.powerstats.service;

import br.com.gubee.interview.core.features.powerstats.repository.PowerStatsRepository;
import br.com.gubee.interview.model.PowerStats;
import br.com.gubee.interview.model.request.UpdateHeroRequest;
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
        return powerStatsRepository.update(powerStats);
    }
    @Override
    @Transactional
    public int deleteById(UUID powerStatsId) {
        return powerStatsRepository.delete(powerStatsId);
    }
}
