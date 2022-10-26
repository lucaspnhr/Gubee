package br.com.gubee.interview.core.features.powerstats;

import br.com.gubee.interview.model.PowerStats;
import br.com.gubee.interview.model.request.UpdateHeroRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PowerStatsServiceImpl implements PowerStatsService {



    private final PowerStatsRepositoryI powerStatsRepository;

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
        PowerStats powerStats = retriveById(powerStatsId);
        powerStats.setDexterity(updateHeroRequest.getDexterity());
        powerStats.setStrength(updateHeroRequest.getStrength());
        powerStats.setIntelligence(updateHeroRequest.getIntelligence());
        powerStats.setDexterity(updateHeroRequest.getDexterity());
        return powerStatsRepository.update(powerStats);
    }
    @Override
    @Transactional
    public int deleteById(UUID powerStatsId) {
        return powerStatsRepository.delete(powerStatsId);
    }
}
