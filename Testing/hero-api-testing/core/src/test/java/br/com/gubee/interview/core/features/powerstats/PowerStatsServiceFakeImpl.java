package br.com.gubee.interview.core.features.powerstats;

import br.com.gubee.interview.model.PowerStats;
import br.com.gubee.interview.model.request.UpdateHeroRequest;

import javax.swing.*;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class PowerStatsServiceFakeImpl implements PowerStatsService{
    /*
    f62be55c-22b3-4f91-a817-56c5da24668c
    76543d70-383a-4770-ab75-3540412bdf34
    dca80f01-4894-4e82-8b46-762e9a236ea4
    c235875d-8966-4428-89cb-8320cd1950c5
    */
    private List<PowerStats> POWER_STATS_REPOSITORY = new ArrayList<>();

    public PowerStatsServiceFakeImpl() {
        this.POWER_STATS_REPOSITORY.add(powerStats("f62be55c-22b3-4f91-a817-56c5da24668c"));
        this.POWER_STATS_REPOSITORY.add(powerStats("76543d70-383a-4770-ab75-3540412bdf34"));
        this.POWER_STATS_REPOSITORY.add(powerStats("dca80f01-4894-4e82-8b46-762e9a236ea4"));
        this.POWER_STATS_REPOSITORY.add(powerStats("c235875d-8966-4428-89cb-8320cd1950c5"));
    }

    @Override
    public UUID create(PowerStats powerStats) {
        powerStats.setId(UUID.randomUUID());
        POWER_STATS_REPOSITORY.add(powerStats);
        return powerStats.getId();
    }

    @Override
    public PowerStats retriveById(UUID id) {
        return POWER_STATS_REPOSITORY.stream()
                .filter(powerStats -> powerStats.getId().equals(id))
                .findFirst().get();
    }

    @Override
    public int update(UpdateHeroRequest updateHeroRequest, UUID powerStatsId) {
        PowerStats toBeUpdate = retriveById(powerStatsId);
        POWER_STATS_REPOSITORY.remove(toBeUpdate);
        toBeUpdate.setAgility(updateHeroRequest.getAgility());
        toBeUpdate.setStrength(updateHeroRequest.getStrength());
        toBeUpdate.setIntelligence(updateHeroRequest.getIntelligence());
        toBeUpdate.setDexterity(updateHeroRequest.getDexterity());
        POWER_STATS_REPOSITORY.add(toBeUpdate);
        return 1;
    }

    @Override
    public int deleteById(UUID powerStatsId) {
        PowerStats toBeRemoved = retriveById(powerStatsId);
        boolean remove = POWER_STATS_REPOSITORY.remove(toBeRemoved);
        return remove ? 1 : 0;
    }


    private static PowerStats powerStats(String id){
        Random r = new Random();

        return PowerStats.builder()
                .id(UUID.fromString(id))
                .agility(r.nextInt(10))
                .dexterity(r.nextInt(10))
                .strength(r.nextInt(10))
                .intelligence(r.nextInt(10))
                .updatedAt(Instant.now())
                .createdAt(Instant.now())
                .build();
    }
}