package br.com.gubee.interview.core.features.powerstats.service;

import br.com.gubee.interview.domain.model.powerstats.PowerStats;
import br.com.gubee.interview.domain.model.request.UpdateHeroRequest;

import java.util.UUID;

public interface PowerStatsService {

    UUID create(PowerStats powerStats);


    PowerStats retriveById(UUID id);


    int update(UpdateHeroRequest updateHeroRequest, UUID powerStatsId);

    int deleteById(UUID powerStatsId);

    void deleteAll();
}
