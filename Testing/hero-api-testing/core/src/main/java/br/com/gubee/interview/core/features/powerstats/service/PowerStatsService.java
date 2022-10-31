package br.com.gubee.interview.core.features.powerstats.service;

import br.com.gubee.interview.model.PowerStats;
import br.com.gubee.interview.model.request.UpdateHeroRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface PowerStatsService {
    @Transactional
    UUID create(PowerStats powerStats);

    @Transactional
    PowerStats retriveById(UUID id);

    @Transactional
    int update(UpdateHeroRequest updateHeroRequest, UUID powerStatsId);

    @Transactional
    int deleteById(UUID powerStatsId);
}
